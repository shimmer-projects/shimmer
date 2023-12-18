package io.github.shimmer.core.converter;

import io.github.shimmer.core.exception.BizException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * <p>字符串转换成LocalDateTime</p>
 * <p>
 * Created on 2023-12-17 11:15
 *
 * @author yu_haiyang
 */
public class StringTimeToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(@NonNull String source) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(source, fmt);
        } catch (DateTimeParseException e) {
            throw new BizException("LocalDateTime 支持接收的时间格式为：yyyy-MM-dd HH:mm:ss, 但接收到的时间格式如下：" + source);
        }
    }
}
