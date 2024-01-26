package io.github.shimmer.authority.entity;

import io.github.shimmer.core.constant.Lock;
import io.github.shimmer.core.constant.Sex;
import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * <p>系统用户实体表</p>
 * Created on 2024-01-12 14:13
 *
 * @author yu_haiyang
 */
@Entity
@Table(name = "sys_user")
@Setter
@Getter
public class SysUserEntity extends BaseEntity<Long> {

    /**
     * 用户名
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /**
     * 账号，用来登录的，不可修改
     */
    @Column(name = "account", nullable = false, updatable = false)
    private String account;

    /**
     * 性别
     * 0 保密；1男； 2 女
     */
    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex = Sex.MAN;

    /**
     * 手机号
     */
    @Column(name = "mobile", unique = true)
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 用户住址
     */
    @Column(name = "address")
    private String address;

    /**
     * 入职时间
     */
    @Column(name = "hiredate", length = 10)
    private Long hiredate;

    /**
     * 锁定标记
     */
    @Column(name = "lock_flag")
    @Enumerated(EnumType.STRING)
    private Lock lockFlag = Lock.UNLOCKED;


    /**
     * 该用户得所属组织
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "organization")
    private SysOrganizationEntity organization;


    /**
     * 该用户得职位信息
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_position_ref",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private List<SysPositionEntity> positions;


    /**
     * 该用户的拥有的角色信息
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "sys_role_user_ref",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<SysRoleEntity> roles;

}
