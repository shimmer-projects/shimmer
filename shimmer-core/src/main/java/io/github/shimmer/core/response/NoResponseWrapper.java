package io.github.shimmer.core.response;

import java.lang.annotation.*;


/**
 * <p>
 * 该注解用来注释对返回结果不进行包装， 作用在controller的类或者是方法上
 * </p>
 * Create on 2023-05-23 13:54
 *
 * @author yu_haiyang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoResponseWrapper {
}
