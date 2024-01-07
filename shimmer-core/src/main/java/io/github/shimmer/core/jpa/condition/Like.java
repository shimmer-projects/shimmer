package io.github.shimmer.core.jpa.condition;

import java.lang.annotation.*;

/**
 * 模糊匹配标注
 *
 * @author yu_haiyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Like {

    Mode value() default Mode.SURROUND;

    /**
     * 目标字段
     *
     * @return 对应entity的字段
     */
    String targetField() default "";

    enum Mode {
        /**
         * 左包含
         */
        LEFT,
        /**
         * 右包含
         */
        RIGHT,
        /**
         * 两侧环绕包含
         */
        SURROUND
    }
}
