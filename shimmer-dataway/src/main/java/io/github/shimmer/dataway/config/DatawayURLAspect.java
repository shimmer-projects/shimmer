package io.github.shimmer.dataway.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * dataway处理的URL请求拦截处理器<br>
 * </p>
 * Created on 2024-02-01 11:20
 *
 * @author yu_haiyang
 */

@Aspect
@Component
public class DatawayURLAspect {

    /**
     * 一个万能接口，所有自定义的URL都走这里，这样就将逻辑转移到了这里;
     * 该种方式暂时制作 application/json格式的请求，暂时不支持文件上传方案，后续考虑自定义RequestMapping处理方案
     */
    @Pointcut("execution(* io.github.shimmer.dataway.controller.DatawayController.*xec(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //解析request请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return joinPoint.proceed();
    }
}
