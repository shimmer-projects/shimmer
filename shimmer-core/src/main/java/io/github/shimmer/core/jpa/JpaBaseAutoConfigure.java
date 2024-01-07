package io.github.shimmer.core.jpa;

import io.github.shimmer.core.jpa.repository.BaseJpaRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>JPA自动装载配置类</p>
 *
 * @author yu_haiyang
 */
@ComponentScan(basePackages = "top.chsi.datasource")
@EntityScan(basePackages = "**.entity")
@EnableJpaAuditing(auditorAwareRef = "cheerDefaultJpaAuditorAware")
@EnableJpaRepositories(basePackages = {"**.repository"}, repositoryBaseClass = BaseJpaRepositoryImpl.class)
public class JpaBaseAutoConfigure {
}

