package io.github.shimmer.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>字符串转换成LocalDate</p>
 * <p>
 * Created on 2023-12-17 11:15
 *
 * @author yu_haiyang
 */
public class StringTimeToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@NonNull String source) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(source, fmt);
    }
}
