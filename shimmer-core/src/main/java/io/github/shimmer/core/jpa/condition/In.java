package io.github.shimmer.core.jpa.condition;

import java.lang.annotation.*;

/**
 * 存在标注，标注字段在数据库中的值在标注字段接收的集合中的值存在即返回。
 *
 * @author yu_haiyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface In {

    /**
     * 目标字段
     *
     * @return 对应entity的字段
     */
    String targetField() default "";

    /**
     * 如果多值是符号分割型的，我们使用指定分隔符进行拆分
     */
    String separator() default ",";

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
