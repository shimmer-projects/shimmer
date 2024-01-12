package io.github.shimmer.core.apiswitch;

import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * <p>定义开关拦截切面</p>
 * Created on 2024-01-10 13:28
 *
 * @author yu_haiyang
 */


@Component
@Aspect
public class ApiSwitchAspect {

    @Resource
    private SwitchResolver resolver;

    @Pointcut("@annotation(apiSwitch)")
    private void api(ApiSwitch apiSwitch) {
    }

    @Around(value = "api(apiSwitch)", argNames = "pjp,apiSwitch")
    public Object ctl(ProceedingJoinPoint pjp, ApiSwitch apiSwitch) throws Throwable {

        // 对应接口开关的key
        String key = apiSwitch.key();

        // 降级方法名
        String fallback = apiSwitch.fallback();

        // 解析器不存在则直接调用目标接口
        if (resolver == null || !resolver.resolver(key)) {
            return pjp.proceed();
        }
        // 调用降级的方法；关于降级的方法简单点，都必须在当前接口类中，同时还不考虑方法参数的情况
        if (!StringUtils.hasLength(fallback)) {
            // 未配置的情况
            throw new RuntimeException("接口不可用！");
        }
        Class<?> clazz = pjp.getSignature().getDeclaringType();
        Method fallbackMethod = clazz.getDeclaredMethod(fallback);
        return fallbackMethod.invoke(pjp.getTarget());
    }
}
