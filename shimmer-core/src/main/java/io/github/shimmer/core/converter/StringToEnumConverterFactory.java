package io.github.shimmer.core.converter;

import io.github.shimmer.core.constant.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

/**
 * <p>字符串转换成枚举类型</p>
 * <p>
 * Created on 2023-12-17 14:17
 *
 * @author yu_haiyang
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum<String>> {

    /**
     * 获取一个从 Integer 转化为 T 的转换器，T 是一个泛型，有多个实现
     *
     * @param targetType 转换后的类型
     * @return 返回一个转化器
     */
    @Override
    @NonNull
    public <T extends BaseEnum<String>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }
}
