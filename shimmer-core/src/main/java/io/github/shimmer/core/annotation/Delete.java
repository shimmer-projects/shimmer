package io.github.shimmer.core.annotation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * 资源标识，此注解代替Spring Mvc的@PostMapping注解
 * <p>
 * 目的是为了在使用Spring Mvc的基础之上，增加对接口权限的控制功能
 *
 * @author yu_haiyang
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(
        method = RequestMethod.DELETE,
        // consumes 标识处理request Content-Type为“application/json”类型的请求.
        consumes = MediaType.ALL_VALUE
)
@ResponseBody
@Operation(method = "DELETE")
public @interface Delete {

    /**
     * 资源名称(必填项)
     */
    @AliasFor(annotation = Operation.class, attribute = "summary")
    String name() default "";

    /**
     * 请求路径(同RequestMapping)
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};

    /**
     * 同RequestMapping
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "produces")
    String[] produces() default {};

    @AliasFor(annotation = Operation.class, attribute = "description")
    String description() default "";
}