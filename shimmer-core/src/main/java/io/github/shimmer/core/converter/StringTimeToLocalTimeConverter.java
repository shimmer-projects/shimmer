package io.github.shimmer.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 字符串转换成LocalTime
 * </p>
 * Created on 2023-12-17 11:15
 *
 * @author yu_haiyang
 */
public class StringTimeToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@NonNull String source) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(source, fmt);
    }
}
