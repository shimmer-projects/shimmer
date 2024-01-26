package io.github.shimmer.authority.entity;

import io.github.shimmer.core.jpa.entity.BaseTreeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统菜单实体表
 *
 * @author yu_haiyang
 */

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "sys_menu")
public class SysMenuEntity extends BaseTreeEntity<SysMenuEntity, Long> {

    /**
     * 路由路径
     */
    private String path;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 菜单相关说明
     */
    private String description;

    /**
     * 菜单元信息
     */
    @OneToOne(cascade = CascadeType.ALL)
    private SysMenuMetaEntity meta;
}
