package io.github.shimmer.core.jpa.wrapper;

import lombok.Builder;
import lombok.Data;

/**
 * <p>查询字段条件式</p>
 * Created on 2024-01-21 11:18
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class QueryField {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 操作符
     */
    private Opt operator;

    /**
     * 值
     */
    private Object value;
}
