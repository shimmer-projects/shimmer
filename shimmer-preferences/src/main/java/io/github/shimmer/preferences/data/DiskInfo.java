package io.github.shimmer.preferences.data;

import io.github.shimmer.core.jackson.serializer.ByteHumanReadable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>磁盘信息</p>
 * Created on 2024-01-18 13:08
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class DiskInfo {

    /**
     * 磁盘驱动器名称
     */
    private String model;

    /**
     * 磁盘序列号
     */
    private String serial;

    /**
     * 磁盘大小
     */
    @ByteHumanReadable
    private long size;

    /**
     * 磁盘的分区
     */
    private List<Partition> partitions;

    @Data
    @Builder
    public static class Partition {

        /**
         * 分区UUID
         */
        private String uuid;
        /**
         * 分区磁盘名称
         */
        private String name;

        /**
         * 分区类型
         */
        private String partitionType;

        /**
         * 文件系统类型
         */
        private String fsType;
        /**
         * 分区磁盘总大小
         */
        @ByteHumanReadable
        private long total;
        /**
         * 已使用大小
         */
        @ByteHumanReadable
        private long usable;
        /**
         * 空闲大小
         */
        @ByteHumanReadable
        private long free;
    }
}
