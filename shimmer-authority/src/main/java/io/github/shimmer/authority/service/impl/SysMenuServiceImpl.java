package io.github.shimmer.authority.service.impl;

import io.github.shimmer.authority.entity.SysMenuEntity;
import io.github.shimmer.authority.mapper.SysMenuMapper;
import io.github.shimmer.authority.repository.SysMenuRepository;
import io.github.shimmer.authority.request.SysMenuRequest;
import io.github.shimmer.authority.response.SysMenuResponse;
import io.github.shimmer.authority.service.SysMenuService;
import io.github.shimmer.core.jpa.pager.PageMapper;
import io.github.shimmer.core.response.data.Pager;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统菜单服务类接口实现
 * <p>
 * Created on 2024-01-12 10:46
 *
 * @author yu_haiyang
 */

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuRepository sysMenuRepository;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Long insert(SysMenuRequest request) {
        SysMenuEntity entity = sysMenuMapper.requestToEntity(request);
        entity.setId(null);
        entity = sysMenuRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        sysMenuRepository.deleteById(id);
    }

    @Override
    public void update(SysMenuRequest request) {
        SysMenuEntity entity = sysMenuRepository.findById(request.id()).orElseThrow();
        entity = sysMenuMapper.requestToEntity(entity, request);
        sysMenuRepository.save(entity);
    }

    @Override
    public SysMenuResponse detail(Long id) {
        SysMenuEntity entity = sysMenuRepository.findById(id).orElseThrow();
        SysMenuResponse response = sysMenuMapper.entityToResponse(entity);
        return response;
    }

    @Override
    public Pager fetch(Pager pager, SysMenuRequest request) {
        PageRequest page = PageRequest.of(pager.getCurrentPage() - 1, pager.getPageSize());
        SysMenuEntity entity = sysMenuMapper.requestToEntity(request);
        Page<SysMenuResponse> entityPage = sysMenuRepository.queryWithConditionEntity(entity, page)
                .map(sysMenuMapper::entityToResponse);
        return PageMapper.mapper(entityPage);
    }

    @Override
    public List<SysMenuResponse> fetchTree() {
        return sysMenuRepository.queryTree().stream().map(sysMenuMapper::entityToResponse).toList();
    }
}
