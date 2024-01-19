package io.github.shimmer.preferences.data;

import io.github.shimmer.core.jackson.serializer.ByteHumanReadable;
import io.github.shimmer.core.jackson.serializer.LongToDate;
import io.github.shimmer.core.jackson.serializer.TimeIntervalHumanReadable;
import lombok.Builder;
import lombok.Data;

/**
 * <p>JVM 信息</p>
 * Created on 2024-01-18 13:08
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class JvmInfo {
    /**
     * JVM总使用内存大小
     */
    @ByteHumanReadable
    private long total;
    /**
     * JVM的最大内容
     */
    @ByteHumanReadable
    private long max;
    /**
     * JVM空闲内容
     */
    @ByteHumanReadable
    private long free;
    /**
     * JVM启动时间
     */
    @LongToDate
    private long startTime;

    /**
     * 启动时长
     */
    @TimeIntervalHumanReadable(millisecond = true)
    private long upTime;

    /**
     * VM 名称
     */
    private String vmName;

    /**
     * JDK版本
     */
    private String javaVersion;

    /**
     * java home
     */
    private String javaHome;

    /**
     * 用户目录
     */
    private String userDir;

}
