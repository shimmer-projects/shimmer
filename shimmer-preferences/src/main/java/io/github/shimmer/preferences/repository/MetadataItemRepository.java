package io.github.shimmer.preferences.repository;

import io.github.shimmer.core.jpa.repository.BaseJpaRepository;
import io.github.shimmer.preferences.entity.MetadataItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>元数据管理</p>
 * Created on 2024-01-22 11:10
 *
 * @author yu_haiyang
 */
@Repository
public interface MetadataItemRepository extends BaseJpaRepository<MetadataItemEntity, Long> {

    @Query(value = "SELECT * FROM prefs_metadata_item WHERE group_id = ?1", nativeQuery = true)
    List<MetadataItemEntity> fetch(Long groupId);
}
