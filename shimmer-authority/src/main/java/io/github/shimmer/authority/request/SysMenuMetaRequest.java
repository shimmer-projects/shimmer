package io.github.shimmer.authority.request;

import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * 系统菜单元信息请求体
 * <p>
 * Created on 2024-01-12 15:27
 *
 * @author yu_haiyang
 */
@Schema(description = "菜单元信息")
public record SysMenuMetaRequest(
        @Schema(description = "菜单图标")
        String icon,
        @NotNull(message = "菜单标题不能为空", groups = {VG.C.class, VG.U.class})
        @Schema(description = "菜单标题名称")
        String title
) {
}
