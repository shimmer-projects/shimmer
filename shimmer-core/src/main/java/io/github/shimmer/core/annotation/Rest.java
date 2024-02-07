package io.github.shimmer.core.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;


/**
 * 资源标识，此注解代替Spring Mvc的@RequestMapping注解
 * <p>
 * 目的是为了在使用Spring Mvc的基础之上，增加对接口权限的控制功能
 *
 * @author yu_haiyang
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Validated
@RestController
@RequestMapping
@Tag(name = "")
public @interface Rest {

    /**
     * 请求路径(同RequestMapping)
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};


    /**
     * 资源名称(必填项)
     */
    @AliasFor(annotation = Tag.class, attribute = "name")
    String name();

    /**
     * 接口说明
     */
    @AliasFor(annotation = Tag.class, attribute = "description")
    String description() default "";
}
