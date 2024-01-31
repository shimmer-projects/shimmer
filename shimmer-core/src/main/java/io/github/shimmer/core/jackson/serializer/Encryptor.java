package io.github.shimmer.core.jackson.serializer;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>jackson数据加解密</p>
 * Created on 2024-01-31 15:19
 *
 * @author yu_haiyang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonDeserialize(using = EncryptorDeserializer.class)
@JsonSerialize(using = EncryptorSerializer.class)
public @interface Encryptor {
    /**
     * 加密方式 base64 rsa res 等，
     * TODO 后续基于枚举实现 , 密钥配置管理，所有参数暂时不实现，初步构想
     */
    String type() default "";

    /**
     * 反序列化的接收类型
     */
    Class<?> clazz() default Object.class;

}