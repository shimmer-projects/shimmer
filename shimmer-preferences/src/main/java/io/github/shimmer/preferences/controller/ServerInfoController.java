package io.github.shimmer.preferences.controller;

import io.github.shimmer.core.annotation.Get;
import io.github.shimmer.core.annotation.Rest;
import io.github.shimmer.preferences.data.ServerInfo;
import io.github.shimmer.preferences.service.ServerInfoService;
import jakarta.annotation.Resource;

import java.net.UnknownHostException;

/**
 * <p>服务器信息控制器</p>
 * Created on 2024-01-18 15:16
 *
 * @author yu_haiyang
 */

@Rest(path = "server", name = "服务器信息")
public class ServerInfoController {

    @Resource
    private ServerInfoService serverInfoService;

    @Get(path = "info", name = "获取服务器相关信息")
    public ServerInfo serverInfo() throws UnknownHostException {
        return serverInfoService.calc();
    }
}
