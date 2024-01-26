package io.github.shimmer.authority.request;

import io.github.shimmer.core.validator.AtLeastOneNotEmpty;
import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * 系统菜单请求参数
 * <p>
 * Created on 2024-01-12 11:13
 *
 * @author yu_haiyang
 */
@AtLeastOneNotEmpty(fields = {"path", "name"}, message = "组件路径与组件名称不能同时为空。")
public record SysMenuRequest(
        @Schema(description = "主键")
        @DecimalMin(value = "1", message = "主键必须是大于0的数值", groups = {VG.U.class})
        @NotNull(message = "主键不能为空", groups = {VG.U.class})
        Long id,

        @Schema(description = "组件路径")
        @NotNull(message = "组件路径不能为空", groups = {VG.C.class, VG.U.class})
        String path,

        @Schema(description = "组件名称")
        @NotNull(message = "组件路径不能为空", groups = {VG.C.class, VG.U.class})
        String name,
        @Valid SysMenuMetaRequest meta
) {
}
