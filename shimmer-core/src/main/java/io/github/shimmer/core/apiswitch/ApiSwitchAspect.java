package io.github.shimmer.core.apiswitch;

import io.github.shimmer.utils.Utils;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

        // 描述信息
        String desc = apiSwitch.desc();

        // 降级方法名
        String fallback = apiSwitch.fallback();
        if (resolver != null && resolver.resolver(key)) {
            // 调用降级的方法；关于降级的方法，都必须在当前接口类中，同时还不支持方法参数
            if (StringUtils.hasLength(fallback)) {
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                Class<?> clazz = signature.getDeclaringType();
                Method method = signature.getMethod();
                Class<?> returnType = method.getReturnType();
                Method fallbackMethod = clazz.getDeclaredMethod(fallback);
                Object invoke = fallbackMethod.invoke(pjp.getTarget());
                if (returnType.isAssignableFrom(fallbackMethod.getReturnType())) {
                    return invoke;
                }
                if (Utils.useNullables(invoke).isNotNull()) {
                    desc = invoke.toString();
                }
            }
            throw new ApiSwitchException(desc);
        }
        return pjp.proceed();
    }
}
