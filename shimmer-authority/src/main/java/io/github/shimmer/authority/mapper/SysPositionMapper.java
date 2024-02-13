package io.github.shimmer.authority.mapper;

import io.github.shimmer.authority.entity.SysPositionEntity;
import io.github.shimmer.authority.request.SysPositionFetchRequest;
import io.github.shimmer.authority.request.SysPositionRequest;
import io.github.shimmer.authority.response.SysPositionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;


/**
 * 职位各实体转换
 * Created on 2024-01-12 15:50
 *
 * @author yu_haiyang
 */
@Mapper(componentModel = "spring")
public interface SysPositionMapper {

    /**
     * 将请求参数转换为 JPA 的数据库实体
     *
     * @param request 请求参数
     * @return 数据库实体 creator, createTime, modifier, modifyTime, tombstone, version
     */
    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "users", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "modifier", ignore = true),
            @Mapping(target = "modifyTime", ignore = true),
            @Mapping(target = "tombstone", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    SysPositionEntity requestToEntity(SysPositionRequest request);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "users", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "modifier", ignore = true),
            @Mapping(target = "modifyTime", ignore = true),
            @Mapping(target = "tombstone", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    SysPositionEntity requestToEntity(SysPositionFetchRequest request);


    /**
     * 请求参数转实体，使用指定的目标对象。
     *
     * @param entity  指定的实体对象
     * @param request 请求参数
     * @return 实体对象
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "users", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "modifier", ignore = true),
            @Mapping(target = "modifyTime", ignore = true),
            @Mapping(target = "tombstone", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    SysPositionEntity requestToEntity(@MappingTarget SysPositionEntity entity, SysPositionRequest request);

    /**
     * 将数据库实体 转换为 用于前端展示的实体
     *
     * @param entity 数据库实体
     * @return 给上游应用的响应信息
     */

    SysPositionResponse entityToResponse(SysPositionEntity entity);
}
