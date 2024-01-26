package io.github.shimmer.authority.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 职位响应体
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString
public class SysPositionResponse {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "岗位编码")
    private String positionCode;

    @Schema(description = "岗位名称")
    private String positionName;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "创建人")
    private Long creator;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private Long modifier;

    @Schema(description = "修改时间")
    private Long modifyTime;
}
