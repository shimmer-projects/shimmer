package io.github.shimmer.dataway;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>数据总线模块自动装载配置</p>
 * Created on 2024-02-01 10:53
 *
 * @author yu_haiyang
 */
@Configuration
@ComponentScan(basePackages = "io.github.shimmer.dataway")
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "shimmer.dataway", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(DatawayProperties.class)
public class DatawayAutoConfig {
}
