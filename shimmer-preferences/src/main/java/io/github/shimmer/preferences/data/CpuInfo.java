package io.github.shimmer.preferences.data;

import lombok.Builder;
import lombok.Data;

/**
 * <p>cpu处理器信息</p>
 * Created on 2024-01-18 13:06
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class CpuInfo {
    /**
     * 物理处理器数量，即内核
     */
    private int physicalProcessorCount;
    /**
     * 逻辑处理器数量
     */
    private int logicalProcessorCount;

    /**
     * 当前系统使用率
     */
    private String sys;
    /**
     * 当前用户使用率
     */
    private String user;
    /**
     * 当前等待率
     */
    private String wait;

    /**
     * 当前空闲率
     */
    private String free;

}
