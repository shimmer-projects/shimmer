package io.github.shimmer.core.audit;

import io.github.shimmer.core.constant.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * <p>审计日志访问AOP拦截处理器</p>
 * Created on 2024-01-15 15:14
 *
 * @author yu_haiyang
 */
@Component
@Aspect
@Slf4j
@Order
@ConditionalOnProperty(prefix = "shimmer.core.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuditLogAspect {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    @Resource
    private ApplicationContext applicationContext;

    @Value("${shimmer.core.audit.enable-global:true}")
    private boolean enableGlobal;


    /**
     * spring aop 一共定义了 五个切入点， Around Before AfterReturning AfterThrowing After
     * 执行顺序 如下：
     * Around -> Before -> (ProceedingJoinPoint.proceed()) -> AfterReturning(AfterThrowing) -> After -> Around
     *
     * @param joinPoint 接入点
     * @return 响应
     * @throws Throwable 业务中的未知异常
     */
    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed(joinPoint.getArgs());
        return proceed;
    }

    /**
     * 声明切点， 拦截所有请求，通过拦截 RequestMapping 等注解实现
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(io.github.shimmer.core.annotation.Get) " +
            "|| @annotation(io.github.shimmer.core.annotation.Put) " +
            "|| @annotation(io.github.shimmer.core.annotation.Post) " +
            "|| @annotation(io.github.shimmer.core.annotation.Delete) " +
            "|| @annotation(io.github.shimmer.core.annotation.Download) " +
            "|| @annotation(io.github.shimmer.core.annotation.Upload) " +
            "|| @annotation(io.github.shimmer.core.audit.AuditLog) "
    )
    public void annotationPointCut() {
    }

    /**
     * 在调用目标方法之前记录一下当前时间
     */
    @Before("annotationPointCut()")
    public void before() {
        // 将开始时间写入
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 该接口执行完之后，清理ThreadLocal中存储的数据
     */
    @After(value = "annotationPointCut()")
    public void after() {
        // 清理ThreadLocal
        startTime.remove();
    }


    /**
     * 在调用完目标方法后记录访问成功的日志
     *
     * @param joinPoint 连接点
     */
    @AfterReturning(value = "annotationPointCut()", returning = "resultVal")
    public void afterReturning(JoinPoint joinPoint, Object resultVal) {
        buildLog(joinPoint, true);
    }

    /**
     * 如果调用过程中发生了异常，记录访问失败日志
     *
     * @param joinPoint 连接点
     */
    @AfterThrowing(value = "annotationPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        buildLog(joinPoint, false);
    }

    private void buildLog(JoinPoint joinPoint, boolean success) {

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        String path = request.getServletPath();
        String clientIp = request.getRemoteAddr();
        String requestMethod = request.getMethod();

        Class<?> targetClass = joinPoint.getTarget().getClass();
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] params = parameterNameDiscoverer.getParameterNames(method);


        long finishTime = System.currentTimeMillis();

        Result result = success ? Result.SUCCESS : Result.FAIL;

        // 包装审计日志
        // TODO 日志内容包装后续实现，完成技术点调试
        if (method.isAnnotationPresent(AuditLog.class)) {
            AuditLog auditLog = method.getAnnotation(AuditLog.class);
            if (auditLog.enable()) {
                // 创建 context 对象
                SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
                // 获取被拦截方法的参数信息
                assert params != null;
                // 把参数配置到上下文中
                for (int i = 0; i < args.length; i++) {
                    context.setVariable(params[i], args[i]);
                }
                // 创建spel的解析对象
                SpelExpressionParser parser = new SpelExpressionParser();
                // 解析el 表达式
                String log = auditLog.content();
                if (auditLog.spel()) {
                    log = parser.parseExpression(auditLog.content()).getValue(context, String.class);
                }

                // USER 通过 CLIENT IP 以 METHOD 方式 访问了 PATH
                AuditLogInfo systemOperationLog = new AuditLogInfo(
                        "",
                        OperationType.READ,
                        requestMethod,
                        "",
                        path,
                        clientIp,
                        log,
                        finishTime - startTime.get(),
                        finishTime,
                        result
                );
                applicationContext.publishEvent(new AuditLogEvent(systemOperationLog));
            }
        } else {
            // TODO 启用全局审计情况，所有接口都进行默认的日志包装
            if (enableGlobal) {
                AuditLogInfo systemOperationLog = new AuditLogInfo(
                        "",
                        OperationType.READ,
                        requestMethod,
                        "",
                        path,
                        clientIp,
                        "默认日志",
                        finishTime - startTime.get(),
                        finishTime,
                        result
                );
                applicationContext.publishEvent(new AuditLogEvent(systemOperationLog));
            }
        }

    }
}
