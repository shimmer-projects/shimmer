package io.github.shimmer.authority.request;

import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 职位信息请求体
 * Created on 2024-01-12 15:22
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "职位信息")
public class SysPositionRequest {
        // 主键
        @Schema(description = "主键")
        @DecimalMin(value = "1", message = "主键必须是大于0的数值", groups = {VG.U.class})
        @NotNull(message = "主键不能为空", groups = {VG.U.class})
        private Long id;


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
