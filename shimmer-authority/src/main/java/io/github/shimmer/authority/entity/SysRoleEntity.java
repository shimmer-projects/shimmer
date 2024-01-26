package io.github.shimmer.authority.entity;

import io.github.shimmer.core.jpa.condition.Like;
import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * <p>系统角色实体表</p>
 * Created on 2024-01-12 14:14
 *
 * @author yu_haiyang
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "sys_role")
public class SysRoleEntity extends BaseEntity<Long> {

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @Like(Like.Mode.RIGHT)
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_role_user_ref",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private List<SysUserEntity> users;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_role_organization_ref",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    @ToString.Exclude
    private List<SysOrganizationEntity> organizations;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_role_position_ref",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    @ToString.Exclude
    private List<SysPositionEntity> positions;

}
