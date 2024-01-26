package io.github.shimmer.authority.mapper;

import io.github.shimmer.authority.entity.SysUserEntity;
import io.github.shimmer.authority.request.SysUserRequest;
import io.github.shimmer.authority.response.SysUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;


/**
 * 系统用户各实体之间的转换
 * Created on 2024-01-12 15:39
 *
 * @author yu_haiyang
 */

@Mapper(componentModel = "spring")
public interface SysUserMapper {

    /**
     * 将请求参数转换为 JPA 的数据库实体
     *
     * @param request 请求参数
     * @return 数据库实体
     */
    @Mappings({
            @Mapping(target = "organization", ignore = true),
            @Mapping(target = "positions", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "modifier", ignore = true),
            @Mapping(target = "modifyTime", ignore = true),
            @Mapping(target = "tombstone", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "lockFlag", ignore = true)
    })
    SysUserEntity requestToEntity(SysUserRequest request);


    /**
     * 请求参数转实体，使用指定的目标对象。
     *
     * @param entity  指定的实体对象
     * @param request 请求参数
     * @return 实体对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "organization", ignore = true),
            @Mapping(target = "positions", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "modifier", ignore = true),
            @Mapping(target = "modifyTime", ignore = true),
            @Mapping(target = "tombstone", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "lockFlag", ignore = true)
    })
    SysUserEntity requestToEntity(@MappingTarget SysUserEntity entity, SysUserRequest request);

    /**
     * 将数据库实体 转换为 用于前端展示的实体
     *
     * @param entity 数据库实体
     * @return 给上游应用的响应信息
     */

    @Mapping(target = "lastLoginTime", ignore = true)
    @Mapping(target = "lastLoginIp", ignore = true)
    SysUserResponse entityToResponse(SysUserEntity entity);
}
