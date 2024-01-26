package io.github.shimmer.authority.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统菜单元数据管理实体表
 *
 * @author yu_haiyang
 */

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "sys_menu_meta")
public class SysMenuMetaEntity extends BaseEntity<Long> {
    private String icon;
    private String title;
    private String frameSrc;
    private Integer showLink;
}
