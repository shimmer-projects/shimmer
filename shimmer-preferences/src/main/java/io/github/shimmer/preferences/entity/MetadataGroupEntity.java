package io.github.shimmer.preferences.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * <p>系统字典元数据组</p>
 * Created on 2024-01-22 10:59
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "metadata_group")
public class MetadataGroupEntity extends BaseEntity<Long> {

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<MetadataItemEntity> items;
}
