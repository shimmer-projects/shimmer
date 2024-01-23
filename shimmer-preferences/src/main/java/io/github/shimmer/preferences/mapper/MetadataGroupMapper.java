package io.github.shimmer.preferences.mapper;

import io.github.shimmer.preferences.entity.MetadataGroupEntity;
import io.github.shimmer.preferences.request.MetadataGroupRequest;
import io.github.shimmer.preferences.response.MetadataGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * <p>元数据组映射</p>
 * Created on 2024-01-23 10:50
 *
 * @author yu_haiyang
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetadataGroupMapper {

    MetadataGroupEntity requestToEntity(MetadataGroupRequest metadataGroup);

    MetadataGroupEntity requestToEntity(@MappingTarget MetadataGroupEntity entity, MetadataGroupRequest request);

    MetadataGroupResponse entityToResponse(MetadataGroupEntity entity);
}
