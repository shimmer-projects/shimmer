package io.github.shimmer.core.response;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


/**
 * <p>基于AOP实现接口响应时间的记录</p>
 * Created on 2024-01-14 17:03
 *
 * @author yu_haiyang
 */
@Aspect
@Component
@Slf4j
public class CostAspect {

    public final ThreadLocal<Long> accessTime = new ThreadLocal<>();
    public final ThreadLocal<Long> completionTime = new ThreadLocal<>();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "|| @annotation(io.github.shimmer.core.annotation.Get) " +
            "|| @annotation(io.github.shimmer.core.annotation.Put) " +
            "|| @annotation(io.github.shimmer.core.annotation.Post) " +
            "|| @annotation(io.github.shimmer.core.annotation.Delete) " +
            "|| @annotation(io.github.shimmer.core.annotation.Download) " +
            "|| @annotation(io.github.shimmer.core.annotation.Upload) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) ")
    public void pointcut() {
    }


    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        accessTime.set(System.currentTimeMillis());
    }

    @AfterReturning(value = "pointcut()", returning = "resultVal")
    public void afterReturning(JoinPoint joinPoint, Object resultVal) {
        completionTime.set(System.currentTimeMillis());
    }

    /**
     * 如果调用过程中发生了异常，记录访问失败日志
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        completionTime.set(System.currentTimeMillis());
    }


    public long cost() {
        // TODO 访问 user/fetch接口的时候，这里抛出异常，需要处理。
        long cost = completionTime.get() - accessTime.get();
        completionTime.remove();
        accessTime.remove();
        return cost;
    }
}
