package io.github.shimmer.authority.repository;

import io.github.shimmer.authority.entity.SysUserEntity;
import io.github.shimmer.core.jpa.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>系统用户ORM查询类</p>
 * Created on 2024-01-12 14:15
 *
 * @author yu_haiyang
 */
@Repository
public interface SysUserRepository extends BaseJpaRepository<SysUserEntity, Long> {

    /**
     * 登录时，通过该接口获取用户信息
     *
     * @param account 用户的登录名
     * @return 用户信息
     */
    @Query("FROM SysUserEntity T WHERE (T.account = ?1) AND T.tombstone = 'UNDELETED'")
    SysUserEntity findByAccount(String account);
}
