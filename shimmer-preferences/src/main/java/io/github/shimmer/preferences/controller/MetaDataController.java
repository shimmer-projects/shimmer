package io.github.shimmer.preferences.controller;

import io.github.shimmer.core.annotation.Get;
import io.github.shimmer.core.annotation.Rest;

/**
 * <p>系统元数据管理，包括字典管理等</p>
 * 元数据管理要分组，每一个分组中包含多个字典值
 * Created on 2024-01-16 16:03
 *
 * @author yu_haiyang
 */
@Rest(path = "/metadata", name = "元数据管理", description = "系统的元数据管理，字典管理等")
public class MetaDataController {

    @Get(path = "/fetch", name = "元数据获取", description = "获取元数据的相关业务")
    public String fetch() {
        return "metadata";
    }
}
