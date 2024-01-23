package io.github.shimmer.preferences.service;

import io.github.shimmer.preferences.request.MetadataGroupRequest;
import io.github.shimmer.preferences.request.MetadataItemRequest;
import io.github.shimmer.preferences.response.MetadataGroupResponse;
import io.github.shimmer.preferences.response.MetadataItemResponse;

import java.util.List;

/**
 * <p>元数据管理</p>
 * Created on 2024-01-22 11:12
 *
 * @author yu_haiyang
 */
public interface MetadataService {

    Long insert(MetadataGroupRequest metadataGroupRequest);

    Long update(MetadataGroupRequest metadataGroupRequest);

    List<MetadataGroupResponse> fetch();

    void delete(Long id);

    void insertItem(MetadataItemRequest metadataItemRequest);

    void deleteItem(Long id);

    void updateItem(MetadataItemRequest metadataItem);

    List<MetadataItemResponse> fetchItems(Long groupId);

    MetadataGroupResponse detailGroup(Long id);

    MetadataItemResponse detailItem(Long id);
}
