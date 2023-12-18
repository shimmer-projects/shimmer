package io.github.shimmer.core.version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>定义API的版本标注</p>
 * Created on 2023-12-18 14:20
 *
 * @author yu_haiyang
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

    /**
     * 默认接口版本号1.0开始，这里我只做了两级，多级可在正则进行控制
     */
    String value() default "1.0";
}
