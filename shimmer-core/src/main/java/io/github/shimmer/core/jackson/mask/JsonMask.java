package io.github.shimmer.core.jackson.mask;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 字段脱敏注解
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = MaskSerializer.class)
public @interface JsonMask {

    /**
     * 脱敏类型
     */
    MaskEnum value();
}
