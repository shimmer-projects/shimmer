package io.github.shimmer.core.response.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>分页对象</p>
 * Created on 2023-12-15 20:36
 *
 * @author yu_haiyang
 */
@Data
@Schema(description = "分页信息")
public class Pager {

    /**
     * 当前页
     */
    @Schema(description = "当前页", requiredMode = Schema.RequiredMode.REQUIRED)
    private int currentPage;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED)
    private int pageSize;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private int pageCount;

    /**
     * 总结记录数
     */
    @Schema(description = "总结记录数")
    private long total;

    /**
     * 是否是第一页
     */

    @Schema(description = "是否第一页")

    private boolean first;

    /**
     * 是否最后一页
     */
    @Schema(description = "是否最后已页")
    private boolean last;

    /**
     * 否否有下一页
     */
    @Schema(description = "是否有下一页")
    private boolean next;

    /**
     * 是否有前一页
     */
    @Schema(description = "是否有前一页")
    private boolean previous;

    /**
     * 数据集
     */
    @JsonIgnore
    private List<?> data;
}
