package io.github.shimmer.preferences.response;

import jakarta.persistence.Column;
import lombok.Data;

/**
 * <p>元数据组响应体</p>
 * Created on 2024-01-23 14:03
 *
 * @author yu_haiyang
 */
@Data
public class MetadataGroupResponse {
    /**
     * 组名称
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * 组编码
     */
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    /**
     * 备注说明
     */
    private String remark;
}
