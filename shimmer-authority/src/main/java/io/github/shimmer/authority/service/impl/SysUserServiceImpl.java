package io.github.shimmer.authority.service.impl;

import io.github.shimmer.authority.entity.SysOrganizationEntity;
import io.github.shimmer.authority.entity.SysPositionEntity;
import io.github.shimmer.authority.entity.SysRoleEntity;
import io.github.shimmer.authority.entity.SysUserEntity;
import io.github.shimmer.authority.mapper.SysUserMapper;
import io.github.shimmer.authority.repository.SysOrganizationRepository;
import io.github.shimmer.authority.repository.SysPositionRepository;
import io.github.shimmer.authority.repository.SysRoleRepository;
import io.github.shimmer.authority.repository.SysUserRepository;
import io.github.shimmer.authority.request.SysUserRequest;
import io.github.shimmer.authority.request.UserRoleRequest;
import io.github.shimmer.authority.response.SysUserResponse;
import io.github.shimmer.authority.service.SysUserService;
import io.github.shimmer.core.response.data.Pager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>系统用户业务接口实现</p>
 * Created on 2024-01-12 14:20
 *
 * @author yu_haiyang
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    //@Resource
//    private PasswordEncoder passwordEncoder;

    private final SysUserRepository sysUserRepository;
    private final SysPositionRepository sysPositionRepository;
    private final SysOrganizationRepository sysOrganizationRepository;
    private final SysRoleRepository sysRoleRepository;

    private final SysUserMapper sysUserMapper;

    @Override
    public Long insert(SysUserRequest request) {
        SysUserEntity entity = sysUserMapper.requestToEntity(request);
        entity.setId(null);
//        String password = entity.getPassword();
//        entity.setPassword(passwordEncoder.encode(password));
        entity = sysUserRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(Long id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public void update(SysUserRequest request) {
        SysUserEntity entity = sysUserRepository.findById(request.id()).orElseThrow();
        sysUserMapper.requestToEntity(entity, request);
        List<Long> position = request.positions();
        List<SysPositionEntity> positions = sysPositionRepository.findAllById(position);
        Optional<SysOrganizationEntity> organization = sysOrganizationRepository.findById(request.organization());
        entity.setOrganization(organization.orElse(null));
        entity.setPositions(positions);
        sysUserRepository.save(entity);
    }

    @Override
    public SysUserResponse detail(Long id) {
        Optional<SysUserEntity> entityOptional = sysUserRepository.findById(id);
        SysUserEntity entity = entityOptional.orElseThrow();
        SysUserResponse response = sysUserMapper.entityToResponse(entity);
        return response;
    }

    @Override
    public Pager<SysUserResponse> fetch(Pager<SysUserResponse> pager, SysUserRequest request) {
        PageRequest page = PageRequest.of(pager.getPageNo() - 1, pager.getPageSize());
        SysUserEntity entity = sysUserMapper.requestToEntity(request);
        Page<SysUserEntity> entities = sysUserRepository.queryWithConditionEntity(entity, page);
        List<SysUserResponse> responses = entities.stream().map(sysUserMapper::entityToResponse).toList();
        pager.setPageCount(entities.getTotalPages());
        pager.setRecordCount(entities.getTotalElements());
        pager.setData(responses);
        return pager;
    }

    @Override
    public void grant(UserRoleRequest request) {
        SysUserEntity user = sysUserRepository.findById(request.userId()).orElseThrow();
        List<SysRoleEntity> roles = sysRoleRepository.findAllById(request.roleId());
        user.setRoles(roles);
        sysUserRepository.save(user);
    }
}
