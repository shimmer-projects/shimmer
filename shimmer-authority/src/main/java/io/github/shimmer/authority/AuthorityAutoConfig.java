package io.github.shimmer.authority;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * <p>认证授权模块自动装载类</p>
 * <p>
 * Created on 2024-01-07 16:53
 *
 * @author yu_haiyang
 */
@Configuration
@EnableConfigurationProperties(AuthorityProperties.class)
@ComponentScan(basePackages = "io.github.shimmer.authority")
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "shimmer.authority", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuthorityAutoConfig {
}
