package io.github.shimmer.preferences.request;

import lombok.Data;

/**
 * <p>字典项请求参数</p>
 * Created on 2024-01-22 16:52
 *
 * @author yu_haiyang
 */
@Data
public class MetadataItemRequest {

    /**
     * item项的ID
     */
    private Long id;
    /**
     * 所属组的ID
     */
    private Long groupId;
    /**
     * 中文可读的名称标题
     */
    private String name;
    /**
     * 程序中使用英文关键字
     */
    private String item;

    /**
     * 程序中对应的字典值
     */
    private String value;
    /**
     * 对该项目的详细说明
     */
    private String remark;
}
