package io.github.shimmer.preferences.service.impl;

import io.github.shimmer.preferences.entity.MetadataGroupEntity;
import io.github.shimmer.preferences.entity.MetadataItemEntity;
import io.github.shimmer.preferences.mapper.MetadataGroupMapper;
import io.github.shimmer.preferences.mapper.MetadataItemMapper;
import io.github.shimmer.preferences.repository.MetadataGroupRepository;
import io.github.shimmer.preferences.repository.MetadataItemRepository;
import io.github.shimmer.preferences.request.MetadataGroupRequest;
import io.github.shimmer.preferences.request.MetadataItemRequest;
import io.github.shimmer.preferences.response.MetadataGroupResponse;
import io.github.shimmer.preferences.response.MetadataItemResponse;
import io.github.shimmer.preferences.service.MetadataService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>元数据管理</p>
 * Created on 2024-01-22 11:12
 *
 * @author yu_haiyang
 */
@Service
public class MetadataServiceImpl implements MetadataService {

    @Resource
    private MetadataGroupRepository metadataGroupRepository;

    @Resource
    private MetadataItemRepository metadataItemRepository;

    @Resource
    private MetadataGroupMapper metadataGroupMapper;

    @Resource
    private MetadataItemMapper metadataItemMapper;


    @Override
    public Long insert(MetadataGroupRequest metadataGroupRequest) {
        MetadataGroupEntity metadataGroupEntity = metadataGroupMapper.requestToEntity(metadataGroupRequest);

        metadataGroupEntity = metadataGroupRepository.save(metadataGroupEntity);
        return metadataGroupEntity.getId();
    }

    @Override
    public Long update(MetadataGroupRequest metadataGroupRequest) {
        MetadataGroupEntity entity = metadataGroupRepository.findById(metadataGroupRequest.getId()).orElse(null);
        entity = metadataGroupMapper.requestToEntity(entity, metadataGroupRequest);
        entity = metadataGroupRepository.save(entity);
        return entity.getId();
    }

    @Override
    public List<MetadataGroupResponse> fetch() {
        return metadataGroupRepository.findAll()
                .stream()
                .map(metadataGroupMapper::entityToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        metadataGroupRepository.deleteById(id);
    }

    @Override
    public void insertItem(MetadataItemRequest metadataItemRequest) {
        MetadataItemEntity metadataItemEntity = metadataItemMapper.requestToEntity(metadataItemRequest);
        MetadataGroupEntity group = metadataGroupRepository.findById(metadataItemRequest.getGroupId()).orElseThrow();
        group.getItems().add(metadataItemEntity);
        metadataGroupRepository.save(group);
    }

    @Override
    public void deleteItem(Long id) {
        metadataItemRepository.deleteById(id);
    }

    @Override
    public void updateItem(MetadataItemRequest metadataItem) {
        MetadataItemEntity metadataItemEntity = metadataItemRepository.findById(metadataItem.getId()).orElseThrow();
        metadataItemEntity = metadataItemMapper.requestToEntity(metadataItemEntity, metadataItem);
        metadataItemRepository.save(metadataItemEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetadataItemResponse> fetchItems(Long groupId) {
        return metadataGroupRepository.findById(groupId)
                .orElseThrow()
                .getItems()
                .stream()
                .map(metadataItemMapper::entityToResponse)
                .toList();
    }

    @Override
    public MetadataGroupResponse detailGroup(Long id) {
        MetadataGroupEntity metadataGroupEntity = metadataGroupRepository.findById(id).orElseThrow();
        return metadataGroupMapper.entityToResponse(metadataGroupEntity);
    }

    @Override
    public MetadataItemResponse detailItem(Long id) {
        MetadataItemEntity metadataItemEntity = metadataItemRepository.findById(id).orElseThrow();
        return metadataItemMapper.entityToResponse(metadataItemEntity);
    }
}
