package io.github.shimmer.preferences.mapper;

import io.github.shimmer.preferences.entity.MetadataItemEntity;
import io.github.shimmer.preferences.request.MetadataItemRequest;
import io.github.shimmer.preferences.response.MetadataItemResponse;
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
public interface MetadataItemMapper {

    MetadataItemEntity requestToEntity(MetadataItemRequest metadataItem);

    MetadataItemEntity requestToEntity(@MappingTarget MetadataItemEntity entity, MetadataItemRequest request);

    MetadataItemResponse entityToResponse(MetadataItemEntity metadataItemEntity);
}
