package io.github.shimmer.preferences.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>服务器信息</p>
 * Created on 2024-01-18 13:05
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class ServerInfo {

    private ComputerInfo computer;
    private CpuInfo cpu;
    private MemoryInfo memory;
    private List<DiskInfo> disk;
    private JvmInfo jvm;
}
