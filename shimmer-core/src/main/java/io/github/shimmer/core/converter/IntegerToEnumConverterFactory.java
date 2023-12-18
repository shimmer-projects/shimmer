package io.github.shimmer.core.converter;

import io.github.shimmer.core.constant.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

/**
 * <p>
 * 数值转换成枚举工厂类
 * </p>
 * Created on 2023-12-17 14:15
 *
 * @author yu_haiyang
 */
public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, BaseEnum<Integer>> {

    /**
     * 获取一个从 Integer 转化为 T 的转换器，T 是一个泛型，有多个实现
     *
     * @param targetType 转换后的类型
     * @return 返回一个转化器
     */
    @Override
    @NonNull
    public <R extends BaseEnum<Integer>> Converter<Integer, R> getConverter(@NonNull Class<R> targetType) {
        return new IntegerToEnumConverter<>(targetType);
    }

}
