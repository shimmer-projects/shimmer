package io.github.shimmer.preferences.data;

import io.github.shimmer.core.jackson.serializer.ByteHumanReadable;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * <p>内存信息</p>
 * Created on 2024-01-18 13:06
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class MemoryInfo {

    /**
     * 内存总大小
     */
    @ByteHumanReadable
    private long total;

    /**
     * 剩余可用内容
     */
    @ByteHumanReadable
    private long available;

    /**
     * 已使用内容
     */
    @ByteHumanReadable
    private long used;

    /**
     * 虚拟内容最大值
     */
    @ByteHumanReadable
    private long virtualMax;
    /**
     * 虚拟内存已使用
     */
    @ByteHumanReadable
    private long virtualInUse;
    /**
     * 最大交换内容
     */
    @ByteHumanReadable
    private long swapTotal;
    /**
     * 已经使用交换内存
     */
    @ByteHumanReadable
    private long swapUsed;

    /**
     * 多个内存条信息
     */
    private List<PhysicalMemory> physicalMemories;

    @Getter
    @Builder
    public static class PhysicalMemory {
        private String manufacturer;
        @ByteHumanReadable
        private long capacity;
        private long clockSpeed;
    }
}
