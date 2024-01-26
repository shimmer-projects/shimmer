package io.github.shimmer.authority.repository;

import io.github.shimmer.authority.entity.SysRoleEntity;
import io.github.shimmer.core.jpa.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>系统角色ORM查询类</p>
 * Created on 2024-01-12 14:15
 *
 * @author yu_haiyang
 */
@Repository
public interface SysRoleRepository extends BaseJpaRepository<SysRoleEntity, Long> {
}
