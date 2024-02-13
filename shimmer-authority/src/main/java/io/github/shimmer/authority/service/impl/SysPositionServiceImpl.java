package io.github.shimmer.authority.service.impl;

import io.github.shimmer.authority.entity.SysPositionEntity;
import io.github.shimmer.authority.mapper.SysPositionMapper;
import io.github.shimmer.authority.repository.SysPositionRepository;
import io.github.shimmer.authority.request.SysPositionFetchRequest;
import io.github.shimmer.authority.request.SysPositionRequest;
import io.github.shimmer.authority.response.SysPositionResponse;
import io.github.shimmer.authority.service.SysPositionService;
import io.github.shimmer.core.jpa.pager.PageMapper;
import io.github.shimmer.core.response.data.Pager;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>职位业务接口实现</p>
 * Created on 2024-01-12 14:21
 *
 * @author yu_haiyang
 */
@Service
public class SysPositionServiceImpl implements SysPositionService {

    @Resource
    private SysPositionRepository sysPositionRepository;

    @Resource
    private SysPositionMapper sysPositionMapper;

    @Override
    public Long insert(SysPositionRequest request) {
        SysPositionEntity entity = sysPositionMapper.requestToEntity(request);
        entity.setId(null);
        entity = sysPositionRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        sysPositionRepository.deleteById(id);
    }

    @Override
    public void update(SysPositionRequest request) {
        SysPositionEntity entity = sysPositionRepository.findById(request.getId()).orElseThrow();
        sysPositionMapper.requestToEntity(entity, request);
        sysPositionRepository.save(entity);
    }

    @Override
    public SysPositionResponse detail(Long id) {
        Optional<SysPositionEntity> entityOptional = sysPositionRepository.findById(id);
        SysPositionEntity entity = entityOptional.orElseThrow();
        SysPositionResponse response = sysPositionMapper.entityToResponse(entity);
        return response;
    }

    @Override
    public Pager fetch(SysPositionFetchRequest request) {
        PageRequest page = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize());
        SysPositionEntity entity = sysPositionMapper.requestToEntity(request);
        Page<SysPositionResponse> entities = sysPositionRepository.queryWithConditionEntity(entity, page)
                .map(sysPositionMapper::entityToResponse);
        Pager mapper = PageMapper.mapper(request, entities);
        return mapper;
    }
}
