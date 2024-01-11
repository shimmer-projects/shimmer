package io.github.shimmer.core;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ShimmerCoreProperties.class)
@ComponentScan(basePackages = "io.github.shimmer.core")
public class ShimmerCoreAutoConfiguration {

}
