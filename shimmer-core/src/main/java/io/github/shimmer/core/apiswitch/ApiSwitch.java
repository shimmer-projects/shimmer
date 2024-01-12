package io.github.shimmer.core.apiswitch;

import java.lang.annotation.*;

/**
 * <p>定义接口开关注解</p>
 * Created on 2024-01-10 13:24
 *
 * @author yu_haiyang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ApiSwitch {

    /**
     * 接口对应的key，通过可以该key查询接口是否关闭
     */
    String key() default "";

    /**
     * 解析器beanName，通过具体的实现获取key对应的值
     */
    String resolver() default "";

    /**
     * 开启后降级方法名
     */
    String fallback() default "";

}
