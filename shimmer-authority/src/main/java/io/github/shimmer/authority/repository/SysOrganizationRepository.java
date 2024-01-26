package io.github.shimmer.authority.repository;

import io.github.shimmer.authority.entity.SysOrganizationEntity;
import io.github.shimmer.core.jpa.repository.BaseTreeJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>组织机构ORM查询类</p>
 * Created on 2024-01-12 14:16
 *
 * @author yu_haiyang
 */
@Repository
public interface SysOrganizationRepository extends BaseTreeJpaRepository<SysOrganizationEntity, Long> {
}
