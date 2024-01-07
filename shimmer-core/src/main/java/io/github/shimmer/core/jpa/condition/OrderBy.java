package io.github.shimmer.core.jpa.condition;

import java.lang.annotation.*;

/**
 * 标注排序字段的注解
 *
 * @author yu_haiyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderBy {

    /**
     * 目标字段
     *
     * @return 对应entity的字段
     */
    String targetField() default "";

    /**
     * 排序序号 1， 2， 3， 4
     *
     * @return 排序的先后顺序，自然数的大小排序
     */
    int order() default 0;

    /**
     * 排序方式
     *
     * @return 排序方式枚举
     */
    Sort sort() default Sort.ASC;

    enum Sort {
        /**
         * 正序
         */
        ASC,
        /**
         * 逆序
         */
        DESC
    }
}
