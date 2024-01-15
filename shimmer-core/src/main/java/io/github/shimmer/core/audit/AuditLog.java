package io.github.shimmer.core.audit;


import java.lang.annotation.*;

/**
 * <p>接口访问日志记录标识注解</p>
 * Created on 2024-01-15 15:04
 *
 * @author yu_haiyang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuditLog {

    /**
     * 是否启用日志功能
     *
     * @return true 启用 false 不启用
     */
    boolean enable() default true;

    /**
     * 基于 SpEL 表达式得审计日志内容
     *
     * @return 日志内容表达式
     */
    String content() default "";

    /**
     * 操作类型
     *
     * @return 增删改查等操作类型
     */
    OperationType type() default OperationType.QUERY;

    /**
     * 是否启用spel表达式
     *
     * @return true 启用，false 不启用； 默认不启用
     */

    boolean spel() default false;
}
