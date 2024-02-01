package io.github.shimmer.utils;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 时间线生成工具
 * Created on 2024-02-01 14:48
 *
 * @author yu_haiyang
 */
public class Timeline {

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 时间线集合
     */
    public static List<String> timeline(Long startTime, Long endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timelines(startTime, endTime, (t) -> t.format(formatter));
    }

    public static List<Long> timelineLongs(Long startTime, Long endTime) {
        return timelines(startTime, endTime, (t) -> t.toEpochSecond(ZoneOffset.ofHours(8)) * 1000);
    }

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param func      转换函数
     * @param <T>       经过func转换之后的类型，由具体业务确定
     * @return 时间线集合
     */
    public static <T> List<T> timelines(Long startTime, Long endTime, Function<LocalDateTime, T> func) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(startTime / 1000, (int) (startTime % 1000), ZoneOffset.of("+8"));
        LocalDateTime et = LocalDateTime.ofEpochSecond(endTime / 1000, (int) (endTime % 100), ZoneOffset.of("+8"));
        List<T> times = new ArrayList<>();
        while (true) {
            int i = localDateTime.compareTo(et);
            if (i > 0) {
                break;
            }
            times.add(func.apply(localDateTime));
            localDateTime = localDateTime.plusDays(1);
        }

        return times;
    }

    /**
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param step            步长
     * @param stepUnit        步长单位
     * @param containEndBound 最后一步时间点可能超过结束时间，如果超过最大值，是否保留结尾边界
     * @return 时间线集合
     */
    public static List<String> timeline(Long startTime, Long endTime, int step, TemporalUnit stepUnit, boolean containEndBound) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(startTime / 1000, (int) (startTime % 1000), ZoneOffset.of("+8"));
        LocalDateTime et = LocalDateTime.ofEpochSecond(endTime / 1000, (int) (endTime % 100), ZoneOffset.of("+8"));
        ArrayList<String> times = new ArrayList<>();
        while (true) {
            int i = localDateTime.compareTo(et);
            if (i > 0) {
                break;
            }
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            times.add(format);
            localDateTime = localDateTime.plus(step, stepUnit);
        }
        // TODO 判断最后一个值是否在结束时间范围内，超出是否移除
        if (!containEndBound) {
            times.remove(times.size() - 1);
        }
        return times;
    }
}
