package io.github.shimmer.core.debounce;

import java.lang.annotation.*;

/**
 * <p>
 * 接口防抖标识注解
 * </p>
 * Created on 2022-12-18 19:38
 *
 * @author yu_haiyang
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Debounce {

    /**
     * 在  seconds 秒内 , 最大只能请求 maxCount 次
     */
    int seconds() default 1;

    /**
     * 最大次数
     */
    int maxCount() default 1;
}
