package io.github.shimmer.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


/**
 * 时间日期相关工具类
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public class Datetime extends Nullables<Long> {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    protected Datetime(Long date) {
        super.source = source;
    }


    protected Datetime(String date) {
        this(Datetime.timeToLongWithPattern(date, DEFAULT_PATTERN));
    }

    protected Datetime(String date, String pattern) {
        this(Datetime.timeToLongWithPattern(date, pattern));
    }


    public Strings format() {
        return format(DEFAULT_PATTERN, ZoneOffset.of("+8"));
    }

    public Strings format(ZoneOffset offset) {
        return format(DEFAULT_PATTERN, offset);
    }

    public Strings format(String pattern, ZoneOffset offset) {
        DateTimeFormatter formatter = getDateTimeFormatter(pattern);
        String format = LocalDateTime.ofEpochSecond(source, 0, offset).format(formatter);
        return new Strings(format);
    }

    private static Long timeToLongWithPattern(String date, String pattern) {
        DateTimeFormatter formatter = Datetime.getDateTimeFormatter(pattern);
        return LocalDateTime.parse(date, formatter).toEpochSecond(ZoneOffset.of("+8"));
    }

    private static DateTimeFormatter getDateTimeFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }
}
