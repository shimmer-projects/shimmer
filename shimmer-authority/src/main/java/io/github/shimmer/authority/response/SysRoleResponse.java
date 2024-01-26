package io.github.shimmer.authority.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 角色响应体
 *
 * @author yu_haiyang
 */
@Data
public class SysRoleResponse {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "备注")
    private String remark;
}
