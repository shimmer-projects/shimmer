package io.github.shimmer.filestore;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>自动装配配置类</p>
 * Created on 2024-01-17 10:55
 *
 * @author yu_haiyang
 */
@Configuration
@EnableConfigurationProperties(FileStoreProperties.class)
@ComponentScan(basePackages = "io.github.shimmer.filestore")
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "shimmer.filestore", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FileStoreAutoConfiguration {
}
