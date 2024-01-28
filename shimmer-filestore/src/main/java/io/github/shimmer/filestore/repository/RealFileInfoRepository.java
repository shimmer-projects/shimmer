package io.github.shimmer.filestore.repository;

import io.github.shimmer.core.jpa.repository.BaseJpaRepository;
import io.github.shimmer.filestore.entity.RealFileInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>物理文件存储的元信息管理</p>
 * Created on 2024-01-26 10:47
 *
 * @author yu_haiyang
 */

@Repository
public interface RealFileInfoRepository extends BaseJpaRepository<RealFileInfoEntity, Long> {
}
