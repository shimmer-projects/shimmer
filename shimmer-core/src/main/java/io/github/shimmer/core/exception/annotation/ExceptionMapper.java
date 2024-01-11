package io.github.shimmer.core.exception.annotation;

import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

/**
 * <p>异常映射注解</p>
 * Created on 2024-01-10 16:53
 *
 * @author yu_haiyang
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionMapper {

    /**
     * 异常对应的错误码.
     *
     * @return 异常对应的错误码
     */
    String code() default "500";

    /**
     * 异常信息.
     *
     * @return 异常对应的提示信息
     */
    String msg() default "系统繁忙!";

    /**
     * 异常信息是否支持替换
     * 仅当msgReplaceable==true，且异常实例的message不为空时才能替换
     */
    boolean msgReplaceable() default true;

    HttpStatus httpStatus() default HttpStatus.OK;
}
