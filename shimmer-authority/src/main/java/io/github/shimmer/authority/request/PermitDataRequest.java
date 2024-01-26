package io.github.shimmer.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Created on 2024-01-12 15:30
 *
 * @author yu_haiyang
 */

@Schema(description = "许可访问数据的请求体")
public record PermitDataRequest(
        @NotNull
        @Schema(description = "角色ID")
        Long roleId,

        @NotNull
        @Schema(description = "数据范围：全部，本部门，本部门及以下，本人，自定义")
        Integer dataScope,


        @Schema(description = "组织ID，当选择自定义的时候，必须传授予访问权限的组织ID")
        List<Long> organizationId
) {
}
