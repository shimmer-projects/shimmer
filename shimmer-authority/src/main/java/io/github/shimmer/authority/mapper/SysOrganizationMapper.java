package io.github.shimmer.authority.mapper;

import io.github.shimmer.authority.entity.SysOrganizationEntity;
import io.github.shimmer.authority.request.SysOrganizationRequest;
import io.github.shimmer.authority.response.SysOrganizationResponse;
import org.mapstruct.*;


/**
 * 组织机构各实体转换
 * Created on 2024-01-12 15:50
 *
 * @author yu_haiyang
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOrganizationMapper {

    /**
     * 将请求参数转换为 JPA 的数据库实体
     *
     * @param request 请求参数
     * @return 数据库实体
     */
    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    SysOrganizationEntity requestToEntity(SysOrganizationRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    SysOrganizationEntity requestToEntity(@MappingTarget SysOrganizationEntity entity, SysOrganizationRequest request);

    /**
     * 将数据库实体 转换为 用于前端展示的实体
     *
     * @param entity 数据库实体
     * @return 给上游应用的响应信息
     */
    SysOrganizationResponse entityToResponse(SysOrganizationEntity entity);
}
