package io.github.shimmer.authority.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 用户角色绑定请求体
 * <p>
 * Created on 2024-01-12 15:22
 *
 * @author yu_haiyang
 */
public record UserRoleRequest(
        @NotNull
        Long userId,
        @NotEmpty
        List<Long> roleId
) {
}
