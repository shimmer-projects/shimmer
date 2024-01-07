package io.github.shimmer.core.jpa.condition;

import java.lang.annotation.*;

/**
 * 等于
 *
 * @author yu_haiyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Equal {

    /**
     * 目标字段
     *
     * @return 对应entity的字段
     */
    String targetField() default "";
}
