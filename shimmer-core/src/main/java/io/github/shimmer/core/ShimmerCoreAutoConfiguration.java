package io.github.shimmer.core;


import io.github.shimmer.core.jpa.repository.BaseTreeJpaRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ShimmerCoreProperties.class)
@ComponentScan(basePackages = "io.github.shimmer.core")
@EntityScan(basePackages = "**.entity")
@EnableJpaAuditing(auditorAwareRef = "jpaDefaultJpaAuditorAware")
@EnableJpaRepositories(basePackages = {"**.repository"}, repositoryBaseClass = BaseTreeJpaRepositoryImpl.class)
public class ShimmerCoreAutoConfiguration {

}
