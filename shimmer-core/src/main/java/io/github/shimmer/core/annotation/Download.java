package io.github.shimmer.core.annotation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.annotation.AliasFor;
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
        method = RequestMethod.GET
)
@ResponseBody
@Operation(
        method = "GET"
)
public @interface Download {

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


    @AliasFor(annotation = Operation.class, attribute = "description")
    String description() default "";

    /**
     * 同RequestMapping
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "produces")
    String[] produces() default {};

    /**
     * 等待下载的文件路径
     * 路径支持以下几种前缀，classpath:, file:, http(s):, store:
     * classpath： 为项目中的resources下
     * file: 本地文件路径
     * http(s): 网络请求路径
     * store: 下载我们filestore中的内容
     */
    String source() default "";

    /**
     * 自定义文件名称，如果接口返回的是InputStream的情况下，该值必须指定，否则没有办法知道该文件以什么类型导出，而且一定药包含后缀
     */
    String filename() default "";
}
