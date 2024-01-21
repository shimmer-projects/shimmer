package io.github.shimmer.core.jpa.wrapper;

import lombok.Builder;
import lombok.Data;

/**
 * <p>排序</p>
 * Created on 2024-01-21 14:27
 *
 * @author yu_haiyang
 */
@Data
@Builder
public class OrderBy {
    /**
     * 属性
     */
    private String property;
    /**
     * 是否是desc
     */
    private boolean isDesc;
}
