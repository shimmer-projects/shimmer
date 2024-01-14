/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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