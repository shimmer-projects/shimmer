package io.github.shimmer.preferences;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>首选项配置类</p>
 * Created on 2024-01-13 16:44
 *
 * @author yu_haiyang
 */
@Data
@ConfigurationProperties(prefix = "shimmer.preferences")
public class ShimmerPreferencesProperties {

    /**
     * [ true | false ] 是否启动首选项模块
     */
    private boolean enabled;
}
