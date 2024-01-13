package io.github.shimmer.preferences;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>首选项配置类</p>
 * Created on 2024-01-13 16:44
 *
 * @author yu_haiyang
 */

@Configuration
@ComponentScan(basePackages = "io.github.shimmer.preferences")
@EnableConfigurationProperties(ShimmerPreferencesProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "shimmer.preferences", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ShimmerPreferencesAutoConfiguration {
}
