package io.github.shimmer.preferences.service;

import io.github.shimmer.preferences.data.*;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>获取系统相关信息</p>
 * Created on 2024-01-18 9:56
 *
 * @author yu_haiyang
 */
@Service
public class ServerInfoService {

    private static final int OSHI_WAIT_SECOND = 1000;

    public ServerInfo calc() throws UnknownHostException {
        Properties env = System.getProperties();
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        // 操作系统相关
        OperatingSystem os = si.getOperatingSystem();
        // 计算机生产信息
        ComputerSystem cs = hal.getComputerSystem();

        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();
        String hostAddress = inetAddress.getHostAddress();

        ComputerInfo computer = ComputerInfo.builder()
                .uuid(cs.getHardwareUUID())
                .serialNumber(cs.getSerialNumber())
                .manufacturer(cs.getManufacturer())
                .os(os.toString())
                .osArch(env.getProperty("os.arch"))
                .address(hostAddress)
                .hostname(hostName)
                .bootTime(os.getSystemBootTime() * 1000)
                .upTime(os.getSystemUptime())
                .build();

        // CPU信息
        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        //cpu系统使用率 sys
        String sys = new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu);
        //cpu用户使用率 user
        String userUsed = new DecimalFormat("#.##%").format(user * 1.0 / totalCpu);
        //cpu当前等待率 wait
        String wait = new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu);
        //cpu当前使用率 free
        String free = new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu));

        CpuInfo cpu = CpuInfo.builder()
                .physicalProcessorCount(processor.getPhysicalProcessorCount())
                .logicalProcessorCount(processor.getLogicalProcessorCount())
                .sys(sys)
                .user(userUsed)
                .wait(wait)
                .free(free)
                .build();

        // 内存信息
        GlobalMemory memory = hal.getMemory();
        VirtualMemory vm = memory.getVirtualMemory();

        List<MemoryInfo.PhysicalMemory> pm = memory.getPhysicalMemory().stream()
                .map(pm1 -> MemoryInfo.PhysicalMemory
                        .builder()
                        .capacity(pm1.getCapacity())
                        .manufacturer(pm1.getManufacturer())
                        .clockSpeed(pm1.getClockSpeed())
                        .build()
                )
                .toList();

        MemoryInfo memoryInfo = MemoryInfo.builder()
                .total(memory.getTotal())
                .available(memory.getAvailable())
                .used(memory.getTotal() - memory.getAvailable())
                .virtualMax(vm.getVirtualMax())
                .virtualInUse(vm.getVirtualInUse())
                .swapTotal(vm.getSwapTotal())
                .swapUsed(vm.getSwapUsed())
                .physicalMemories(pm)
                .build();

        // 硬盘信息
        List<HWDiskStore> diskStores = hal.getDiskStores();

        // 磁盘信息
        List<OSFileStore> fileStores = os.getFileSystem().getFileStores();
        Map<String, DiskInfo.Partition> partitionMap = fileStores.stream().map(fs -> DiskInfo.Partition.builder()
                        .uuid(fs.getUUID())
                        .name(fs.getName())
                        .fsType(fs.getType())
                        .total(fs.getTotalSpace())
                        .usable(fs.getUsableSpace())
                        .free(fs.getFreeSpace())
                        .build())
                .collect(Collectors.toMap(DiskInfo.Partition::getUuid, t -> t));
        List<DiskInfo> diskInfos = diskStores.stream().map(hds -> {
            List<DiskInfo.Partition> partitions = hds.getPartitions().stream()
                    .map(partition -> partitionMap.get(partition.getUuid()).setPartitionType(partition.getType()))
                    .toList();
            return DiskInfo.builder()
                    .model(hds.getModel())
                    .serial(hds.getSerial())
                    .size(hds.getSize())
                    .partitions(partitions)
                    .build();
        }).toList();

        // JVM 信息
        Runtime runtime = Runtime.getRuntime();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        JvmInfo jvm = JvmInfo.builder()
                .total(runtime.totalMemory())
                .max(runtime.maxMemory())
                .free(runtime.freeMemory())
                .startTime(runtimeMXBean.getStartTime())
                .upTime(runtimeMXBean.getUptime())
                .vmName(runtimeMXBean.getVmName())
                .javaVersion(env.getProperty("java.version"))
                .javaHome(env.getProperty("java.home"))
                .userDir(env.getProperty("user.dir"))
                .build();

        return ServerInfo.builder()
                .computer(computer)
                .cpu(cpu)
                .memory(memoryInfo)
                .disk(diskInfos)
                .jvm(jvm)
                .build();
    }

}
