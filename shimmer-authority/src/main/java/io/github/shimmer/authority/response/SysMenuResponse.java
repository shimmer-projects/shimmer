package io.github.shimmer.authority.response;

import io.github.shimmer.authority.entity.SysMenuMetaEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单响应信息
 * <p>
 * Created on 2024-01-12 11:16
 *
 * @author yu_haiyang
 */

@Data
public class SysMenuResponse {

    @Schema(description = "菜单路径路由")
    private String path;

    @Schema(description = "菜单名称路由")
    private String name;

    @Schema(description = "菜单说明")
    private String description;

    @Schema(description = "菜单元信息")
    private SysMenuMetaEntity meta;

    @Schema(description = "父级ID")
    private Long pid;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建人")
    private Long creator;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private Long modifier;

    @Schema(description = "修改时间")
    private Long modifyTime;

    @Schema(description = "子菜单")
    private List<SysMenuResponse> children = new ArrayList<>();
}
