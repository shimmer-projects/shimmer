package io.github.shimmer.core.jpa.entity;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * TODO 暂未实现
 * <p>
 * 自定义转换器: 入库加密,出库解密.
 * Converter属性autoApply 需默认或为false,否则未指定对的字段也会被加密
 * </p>
 * Created on 2024-01-06 16:35
 *
 * @author yu_haiyang
 */
@Converter
public class EncryptConverter implements AttributeConverter<String, String> {
    /**
     * 加密.
     */
    @Override
    public String convertToDatabaseColumn(String text) {
        // TODO ... 加解密实现
        return "密文";
    }

    /**
     * 解密.
     */
    @Override
    public String convertToEntityAttribute(String s) {
        // TODO ... 加解密实现
        return "明文";
    }
}
