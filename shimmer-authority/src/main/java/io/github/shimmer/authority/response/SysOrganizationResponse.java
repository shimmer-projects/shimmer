package io.github.shimmer.authority.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 组织机构响应体
 *
 * @author yu_haiyang
 */
@Data
public class SysOrganizationResponse {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "组织编码")
    private String orgCode;

    @Schema(description = "组织名称")
    private String orgName;

    @Schema(description = "父级组织id")
    private Long pid;

    @Schema(description = "负责人")
    private String director;
    @Schema(description = "组织状态")
    private int state = 1;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "子组织")
    private List<SysOrganizationResponse> children;

}
