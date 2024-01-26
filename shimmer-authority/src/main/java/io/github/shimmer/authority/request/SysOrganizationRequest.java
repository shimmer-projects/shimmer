package io.github.shimmer.authority.request;

import io.github.shimmer.authority.entity.SysOrganizationEntity;
import io.github.shimmer.core.validator.Exist;
import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 组织机构请求体
 * Created on 2024-01-12 15:22
 *
 * @author yu_haiyang
 */
@Schema(description = "组织信息")
@Exist.List({
        @Exist(entity = SysOrganizationEntity.class, sourceId = "id", sourceField = "orgCode", groups = {VG.C.class, VG.U.class}),
        @Exist(entity = SysOrganizationEntity.class, sourceId = "id", sourceField = "orgName", groups = {VG.C.class, VG.U.class})
})
public record SysOrganizationRequest(
        @Schema(description = "主键")
        @DecimalMin(value = "1", message = "主键必须是大于0的数值", groups = {VG.U.class})
        @NotNull(message = "主键不能为空", groups = {VG.U.class})
        Long id,

        @Schema(description = "组织名称")
        @NotBlank(message = "组织名称不能为空", groups = {VG.C.class, VG.U.class})
        String orgName,

        @Schema(description = "组织编码")
        @NotBlank(message = "组织编码不能为空", groups = {VG.C.class, VG.U.class})
        String orgCode,

        @Schema(description = "父组织ID")
        @DecimalMin(message = "父组织ID必须是大于0的整数", value = "1",
                groups = {VG.C.class, VG.U.class})
        Long pid,

        @Schema(description = "负责人")
        String director,
        @Schema(description = "组织状态")
        Integer state,
        @Schema(description = "备注")
        String remark
) {
}
