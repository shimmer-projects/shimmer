package io.github.shimmer.preferences.data;

import io.github.shimmer.core.jackson.serializer.LongToDate;
import lombok.Builder;
import lombok.Data;

/**
 * <p>计算机固件信息</p>
 * Created on 2024-01-18 13:07
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class ComputerInfo {

    /**
     * 应将的唯一ID
     */
    private String uuid;
    /**
     * 主板序列号
     */
    private String serialNumber;
    /**
     * 主机厂商
     */
    private String manufacturer;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 系统架构
     */
    private String osArch;

    /**
     * 服务器IP
     */
    private String address;
    /**
     * 服务器主机名称
     */
    private String hostname;
    /**
     * 启动时间
     */
    @LongToDate
    private long bootTime;
    /**
     * 运行时长, 单位：秒
     */
    private long upTime;

    /**
     * 用户目录
     */
    private String userDir;
}
