package io.github.shimmer.preferences.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


/**
 * <p>系统字典元数据项</p>
 * Created on 2024-01-22 11:00
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "prefs_metadata_item")
public class MetadataItemEntity extends BaseEntity<Long> {

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

    private Long groupId;
}
