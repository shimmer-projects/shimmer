package io.github.shimmer.authority.repository;

import io.github.shimmer.authority.entity.SysMenuEntity;
import io.github.shimmer.core.jpa.repository.BaseTreeJpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 系统菜单获取数据请求库
 * <p>
 * Created on 2024-01-12 10:44
 *
 * @author yu_haiyang
 */
@Repository
public interface SysMenuRepository extends BaseTreeJpaRepository<SysMenuEntity, Long> {
}
