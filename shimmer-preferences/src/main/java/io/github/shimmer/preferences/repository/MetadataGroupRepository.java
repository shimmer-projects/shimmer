package io.github.shimmer.preferences.repository;

import io.github.shimmer.core.jpa.repository.BaseJpaRepository;
import io.github.shimmer.preferences.entity.MetadataGroupEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>元数据管理</p>
 * Created on 2024-01-22 11:10
 *
 * @author yu_haiyang
 */
@Repository
public interface MetadataGroupRepository extends BaseJpaRepository<MetadataGroupEntity, Long> {
}
