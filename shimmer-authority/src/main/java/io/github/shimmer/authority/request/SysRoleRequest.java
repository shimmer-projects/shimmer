package io.github.shimmer.authority.request;

import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 用户角色请求体
 * Created on 2024-01-12 15:22
 *
 * @author yu_haiyang
 */
@Schema(description = "角色信息")
public record SysRoleRequest(
        // 主键
        @DecimalMin(value = "1", message = "主键必须是大于0的数值", groups = VG.U.class)
        @NotNull(message = "主键不能为空", groups = VG.U.class)
        @Schema(description = "主键")
        Long id,

        // 角色名称
        @Schema(description = "角色名称")
        @NotBlank(message = "角色名称不能为空")
        String roleName,

        // 角色编码
        @Schema(description = "角色编码")
        String roleCode,

        // 备注
        @Schema(description = "备注")
        String remark
) {
}
