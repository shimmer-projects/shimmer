package io.github.shimmer.preferences.request;

import io.github.shimmer.core.validator.VG;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>元数据组前端请求参数</p>
 * Created on 2024-01-22 16:38
 *
 * @author yu_haiyang
 */
@Data
public class MetadataGroupRequest {

    @NotNull(groups = VG.U.class)
    private Long id;
    /**
     * 组名称
     */
    @NotBlank
    private String name;

    /**
     * 组编码
     */
    @NotBlank
    private String code;
    /**
     * 备注说明
     */
    private String remark;

}
