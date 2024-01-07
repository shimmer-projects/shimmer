package io.github.shimmer.core.jpa;

import io.github.shimmer.core.jpa.repository.BaseTreeJpaRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>JPA自动装载配置类</p>
 *
 * @author yu_haiyang
 */
@EnableJpaRepositories(basePackages = {"**.repository"}, repositoryBaseClass = BaseTreeJpaRepositoryImpl.class)
public class JpaBaseTreeAutoConfigure {
}

