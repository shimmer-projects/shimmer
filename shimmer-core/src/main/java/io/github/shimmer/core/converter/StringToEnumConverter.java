package io.github.shimmer.core.converter;

import io.github.shimmer.core.constant.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 字符串转换成枚举类型
 * </p>
 * Created on 2023-12-17 14:16
 *
 * @author yu_haiyang
 */
public class StringToEnumConverter<T extends BaseEnum<String>> implements Converter<String, T> {
    private final Map<String, T> enumMap = new HashMap<>();

    public StringToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getCode(), e);
        }
    }

    @Override
    public T convert(@NonNull String source) {
        T t = enumMap.get(source);
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
