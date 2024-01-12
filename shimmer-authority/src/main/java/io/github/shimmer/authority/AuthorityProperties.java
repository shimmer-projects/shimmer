package io.github.shimmer.authority;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>认证授权模块配置信息</p>
 * Created on 2024-01-12 9:43
 *
 * @author yu_haiyang
 */

@Data
@ConfigurationProperties(prefix = "shimmer.authority")
public class AuthorityProperties {

    /**
     * 是否启用认证授权模块
     */
    private boolean enabled = true;
}
