package io.github.shimmer.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>字符串转换成时间</p>
 * <p>
 * Created on 2023-12-17 11:15
 *
 * @author yu_haiyang
 */
public class StringTimeToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(@NonNull String source) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException("时间转换异常，支持格式为：yyyy-MM-dd HH:mm:ss", e);
        }
    }
}
