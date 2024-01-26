package io.github.shimmer.authority.service.impl;

import io.github.shimmer.authority.entity.SysOrganizationEntity;
import io.github.shimmer.authority.mapper.SysOrganizationMapper;
import io.github.shimmer.authority.repository.SysOrganizationRepository;
import io.github.shimmer.authority.request.SysOrganizationRequest;
import io.github.shimmer.authority.response.SysOrganizationResponse;
import io.github.shimmer.authority.service.SysOrganizationService;
import io.github.shimmer.core.response.data.Pager;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>组织机构业务接口实现</p>
 * Created on 2024-01-12 14:21
 *
 * @author yu_haiyang
 */
@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {

    @Resource
    private SysOrganizationRepository sysOrganizationRepository;

    @Resource
    private SysOrganizationMapper sysOrganizationMapper;

    @Override
    public Long insert(SysOrganizationRequest request) {
        SysOrganizationEntity entity = sysOrganizationMapper.requestToEntity(request);
        entity.setId(null);
        entity = sysOrganizationRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        sysOrganizationRepository.deleteById(id);
    }

    @Override
    public void update(SysOrganizationRequest request) {
        SysOrganizationEntity entity = sysOrganizationRepository.findById(request.id()).orElseThrow();
        sysOrganizationMapper.requestToEntity(entity, request);
        sysOrganizationRepository.save(entity);
    }

    @Override
    public SysOrganizationResponse detail(Long id) {
        Optional<SysOrganizationEntity> entityOptional = sysOrganizationRepository.findById(id);
        SysOrganizationEntity entity = entityOptional.orElseThrow();
        SysOrganizationResponse response = sysOrganizationMapper.entityToResponse(entity);
        return response;
    }

    @Override
    public Pager<SysOrganizationResponse> fetch(Pager<SysOrganizationResponse> pager, SysOrganizationRequest request) {
        PageRequest page = PageRequest.of(pager.getPageNo() - 1, pager.getPageSize());
        SysOrganizationEntity entity = sysOrganizationMapper.requestToEntity(request);
        Page<SysOrganizationResponse> entities = sysOrganizationRepository.queryWithConditionEntity(entity, page)
                .map(sysOrganizationMapper::entityToResponse);
        pager.setPageCount(entities.getTotalPages());
        pager.setRecordCount(entities.getTotalElements());
        pager.setData(entities.getContent());
        return pager;
    }

    @Override
    public List<SysOrganizationResponse> fetchTree() {
        Stream<SysOrganizationEntity> stream = sysOrganizationRepository.queryTree().stream();
        List<SysOrganizationResponse> list = stream
                .map(sysOrganizationMapper::entityToResponse)
                .toList();
        return list;
    }
}
