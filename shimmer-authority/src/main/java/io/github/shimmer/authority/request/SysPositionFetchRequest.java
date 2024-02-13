package io.github.shimmer.authority.request;

import io.github.shimmer.core.response.data.Pager;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * <p>条件查询参数</p>
 * Created on 2024-02-12 12:56
 *
 * @author yu_haiyang
 */
public class SysPositionFetchRequest extends Pager {

    // 岗位名称
    @Schema(description = "职位名称")
    @NotBlank(message = "职位名称不能为空")
    private String positionName;

    // 岗位编码
    @Schema(description = "职位编码")
    @NotBlank(message = "职位编码不能为空")
    private String positionCode;

    // 备注信息
    @Schema(description = "备注信息")
    private String remark;
}
