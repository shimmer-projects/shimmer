package io.github.shimmer.core.jackson.splits;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <p>拆分扩展序列化注解</p>
 * <p>该注解接收两个参数，一个是分隔符，一个是按索引位置定义的字段属性。</p>
 * <p>
 * example:
 * 原始字段的值是： A,B,C
 * 我们指定的分隔符是 ","
 * 指定的接收字段是， name, email, role
 * 序列化之后，Json中会额外增加三个字段
 * 1. name=A
 * 2. email=B
 * 3. role=C
 *
 * @author yu_haiyang
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SplitSerializer.class)
public @interface Split {


    /**
     * 分割字符
     */
    String separator() default ",";

    /**
     * 指定扩展出来的字段
     */
    String[] fields();
}
