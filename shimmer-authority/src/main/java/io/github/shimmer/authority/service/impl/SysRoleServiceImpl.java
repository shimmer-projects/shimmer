package io.github.shimmer.authority.service.impl;

import io.github.shimmer.authority.entity.SysRoleEntity;
import io.github.shimmer.authority.mapper.SysRoleMapper;
import io.github.shimmer.authority.repository.SysRoleRepository;
import io.github.shimmer.authority.request.SysRoleRequest;
import io.github.shimmer.authority.response.SysRoleResponse;
import io.github.shimmer.authority.service.SysRoleService;
import io.github.shimmer.core.response.data.Pager;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>角色业务接口实现</p>
 * Created on 2024-01-12 14:21
 *
 * @author yu_haiyang
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleRepository sysRoleRepository;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Long insert(SysRoleRequest request) {
        SysRoleEntity entity = sysRoleMapper.requestToEntity(request);
        entity.setId(null);
        entity = sysRoleRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        sysRoleRepository.deleteById(id);
    }

    @Override
    public void update(SysRoleRequest request) {
        SysRoleEntity entity = sysRoleRepository.findById(request.id()).orElseThrow();
        sysRoleMapper.requestToEntity(entity, request);
        sysRoleRepository.save(entity);
    }

    @Override
    public SysRoleResponse detail(Long id) {
        Optional<SysRoleEntity> entityOptional = sysRoleRepository.findById(id);
        SysRoleEntity entity = entityOptional.orElseThrow();
        SysRoleResponse response = sysRoleMapper.entityToResponse(entity);
        return response;
    }

    @Override
    public Pager<SysRoleResponse> fetch(Pager<SysRoleResponse> pager, SysRoleRequest request) {
        PageRequest page = PageRequest.of(pager.getPageNo() - 1, pager.getPageSize());
        SysRoleEntity entity = sysRoleMapper.requestToEntity(request);
        Page<SysRoleEntity> entities = sysRoleRepository.queryWithConditionEntity(entity, page);
        List<SysRoleResponse> responses = entities.stream().map(sysRoleMapper::entityToResponse).toList();
        pager.setPageCount(entities.getTotalPages());
        pager.setRecordCount(entities.getTotalElements());
        pager.setData(responses);
        return pager;
    }

}
