package io.github.shimmer.codegen;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>自动生成配置</p>
 * Created on 2024-02-04 16:30
 *
 * @author yu_haiyang
 */

@Configuration
@ConditionalOnWebApplication
@ComponentScan(basePackages = "io.github.shimmer.codegen")
@ConditionalOnProperty(prefix = "shimmer.codegen", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(CodegenProperties.class)
public class CodegenAutoConfig {
}
