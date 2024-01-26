package io.github.shimmer.authority.repository;

import io.github.shimmer.authority.entity.SysPositionEntity;
import io.github.shimmer.core.jpa.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>职位ORM查询类</p>
 * Created on 2024-01-12 14:15
 *
 * @author yu_haiyang
 */
@Repository
public interface SysPositionRepository extends BaseJpaRepository<SysPositionEntity, Long> {
}
