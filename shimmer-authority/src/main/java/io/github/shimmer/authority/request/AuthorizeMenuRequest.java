package io.github.shimmer.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 授权角色可以访问的菜单的请求体
 * <p>
 * Created on 2024-01-12 15:27
 *
 * @author yu_haiyang
 */
@Schema(description = "授权角色可以访问的菜单的请求体")
public record AuthorizeMenuRequest(
        @NotNull
        @Schema(description = "角色ID")
        Long roleId,
        @NotEmpty
        @NotNull
        @Schema(description = "菜单ID集合")
        List<Long> menuId
) {
}
