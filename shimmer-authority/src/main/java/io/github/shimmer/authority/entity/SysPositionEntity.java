package io.github.shimmer.authority.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 职位实体表
 * Created on 2024-01-12 14:14
 *
 * @author yu_haiyang
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "sys_position")
public class SysPositionEntity extends BaseEntity<Long> {

    /**
     * 岗位名称
     */
    @Column(name = "position_name")
    private String positionName;

    /**
     * 岗位编码
     */
    @Column(name = "position_code")
    private String positionCode;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;


    /**
     * 职位与用户的多对多关系
     */
    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "sys_user_position_ref",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<SysUserEntity> users;

    /**
     * 职位与角色的多对多关系，可以为职位添加通用角色
     */
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_role_position_ref",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private List<SysRoleEntity> roles;

}
