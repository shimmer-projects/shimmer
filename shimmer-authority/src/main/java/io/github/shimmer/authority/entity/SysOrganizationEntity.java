package io.github.shimmer.authority.entity;

import io.github.shimmer.core.jpa.condition.Equal;
import io.github.shimmer.core.jpa.condition.Like;
import io.github.shimmer.core.jpa.entity.BaseTreeEntity;
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
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "sys_organization")
public class SysOrganizationEntity extends BaseTreeEntity<SysOrganizationEntity, Long> {

    /**
     * 组织编码
     */
    @Column(name = "org_code", nullable = false, updatable = false, unique = true)
    @Equal
    private String orgCode;

    /**
     * 组织名称
     */
    @Column(name = "org_name", nullable = false, unique = true)
    @Like
    private String orgName;

    /**
     * 责任人
     */
    @Column(name = "director", nullable = false, unique = false)
    private String director;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 组织状态
     */
    @Column(name = "state")
    @Equal
    private Integer state;


    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SysUserEntity> users;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_role_organization_ref",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private List<SysRoleEntity> roles;

}
