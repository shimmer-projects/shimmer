package io.github.shimmer.authority.mapper;

import io.github.shimmer.authority.entity.SysMenuEntity;
import io.github.shimmer.authority.request.SysMenuRequest;
import io.github.shimmer.authority.response.SysMenuResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


/**
 * 系统菜单各实体转换关系
 * <p>
 * Created on 2024-01-12 14:13
 *
 * @author yu_haiyang
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuMapper {

    SysMenuEntity requestToEntity(SysMenuRequest request);

    SysMenuEntity requestToEntity(@MappingTarget SysMenuEntity entity, SysMenuRequest request);

    SysMenuResponse entityToResponse(SysMenuEntity entity);
}
