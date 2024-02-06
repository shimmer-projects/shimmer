package io.github.shimmer.dataway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>数据总线模块配置</p>
 * Created on 2024-02-01 10:53
 *
 * @author yu_haiyang
 */
@Data
@ConfigurationProperties(prefix = "shimmer.dataway")
public class DatawayProperties {

    /**
     * 是否启用 dataway 模块
     */
    private boolean enabled = true;

    /**
     * 基础路径
     */
    private String basePath = "/api/";
}
