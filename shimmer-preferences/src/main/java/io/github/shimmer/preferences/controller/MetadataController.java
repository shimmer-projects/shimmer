package io.github.shimmer.preferences.controller;

import io.github.shimmer.core.annotation.*;
import io.github.shimmer.core.validator.VG;
import io.github.shimmer.preferences.request.MetadataGroupRequest;
import io.github.shimmer.preferences.request.MetadataItemRequest;
import io.github.shimmer.preferences.response.MetadataGroupResponse;
import io.github.shimmer.preferences.response.MetadataItemResponse;
import io.github.shimmer.preferences.service.MetadataService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>系统元数据管理，包括字典管理等</p>
 * 元数据管理要分组，每一个分组中包含多个字典值
 * Created on 2024-01-16 16:03
 *
 * @author yu_haiyang
 */
@Rest(path = "/metadata", name = "元数据管理", description = "系统的元数据管理，字典管理等")
public class MetadataController {

    @Resource
    private MetadataService metadataService;

    @Post(path = "group/insert", name = "新增元数据组")
    public Long insertGroup(@Validated(VG.C.class) @RequestBody MetadataGroupRequest metadataGroupRequest) {
        return metadataService.insert(metadataGroupRequest);
    }

    @Get(path = "group/detail", name = "删除元数据组")
    public void detailGroup(@Validated
                            @NotNull(message = "用户ID不能为空")
                            @DecimalMin(value = "1", message = "用户ID不能小于1")
                            @Parameter(name = "id", description = "主键") Long id) {
        metadataService.detailGroup(id);
    }

    @Put(path = "group/update", name = "更新元数据组")
    public Long updateGroup(@Validated(VG.U.class) @RequestBody MetadataGroupRequest metadataGroupRequest) {
        return metadataService.update(metadataGroupRequest);
    }

    @Delete(path = "group/delete", name = "删除元数据组")
    public void deleteGroup(@Validated
                            @NotNull(message = "用户ID不能为空")
                            @DecimalMin(value = "1", message = "用户ID不能小于1")
                            @Parameter(name = "id", description = "主键") Long id) {
        metadataService.delete(id);
    }

    @Get(path = "group/fetch", name = "获取字典元数据组")
    public List<MetadataGroupResponse> fetch() {
        return metadataService.fetch();
    }

    @Get(path = "item/fetch", name = "根据组ID获取元数据项")
    public List<MetadataItemResponse> fetchItems(Long groupId) {
        return metadataService.fetchItems(groupId);
    }

    @Post(path = "item/insert", name = "新增元数据项")
    public void insertItem(@Validated(VG.C.class) @RequestBody MetadataItemRequest metadataItem) {
        metadataService.insertItem(metadataItem);
    }

    @Get(path = "item/detail", name = "获取元数据项详情")
    public MetadataItemResponse detailItem(@Validated
                                           @NotNull(message = "用户ID不能为空")
                                           @DecimalMin(value = "1", message = "用户ID不能小于1")
                                           @Parameter(name = "id", description = "主键") Long id) {
        return metadataService.detailItem(id);
    }

    @Delete(path = "item/delete", name = "删除元数据项")
    public void deleteItem(@Validated
                           @NotNull(message = "用户ID不能为空")
                           @DecimalMin(value = "1", message = "用户ID不能小于1")
                           @Parameter(name = "id", description = "主键") Long id) {
        metadataService.deleteItem(id);
    }

    @Put(path = "item/update", name = "更新元数据项")
    public void updateItem(@Validated(VG.U.class) @RequestBody MetadataItemRequest metadataItem) {
        metadataService.updateItem(metadataItem);
    }
}
