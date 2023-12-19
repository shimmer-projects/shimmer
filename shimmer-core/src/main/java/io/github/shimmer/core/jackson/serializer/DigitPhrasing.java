package io.github.shimmer.core.jackson.serializer;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 数字序列化操作注解，使用该注解标记的数值型字段，数字将会序列化为科学计算三位逗号分割的形式
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = DigitPhrasingSerializer.class)
public @interface DigitPhrasing {
}
