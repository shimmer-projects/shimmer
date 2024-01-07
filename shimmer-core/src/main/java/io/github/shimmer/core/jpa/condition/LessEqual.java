package io.github.shimmer.core.jpa.condition;

import java.lang.annotation.*;

/**
 * 小于等于标注
 *
 * @author yu_haiyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LessEqual {

    /**
     * 目标字段
     *
     * @return 对应entity的字段
     */
    String targetField() default "";

    /**
     * 是否是时间
     *
     * @return true 明该字段需要就行时间格式化处理，否则不需要
     */
    boolean isTime() default false;

    /**
     * 时间格式
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    String timeScheme() default "yyyy-MM-dd HH:mm:ss";
}
