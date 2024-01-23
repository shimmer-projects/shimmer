package io.github.shimmer.preferences.response;

import lombok.Data;

/**
 * <p>元数据项的响应体</p>
 * Created on 2024-01-23 14:10
 *
 * @author yu_haiyang
 */
@Data
public class MetadataItemResponse {

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
