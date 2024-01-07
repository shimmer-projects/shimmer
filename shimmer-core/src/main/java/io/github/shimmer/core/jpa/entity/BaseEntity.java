package io.github.shimmer.core.jpa.entity;

import io.github.shimmer.core.constant.Delete;
import io.github.shimmer.core.jpa.JpaAuditingListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * <p>
 * 对一个实体类而言，主键、乐观锁、常用的审计字段等是各个表中不可或缺的字段。
 * 此类字段与业务本身也并没有关系，如果在每一个实体类中都去定义的话，难免会出现以下几种问题：
 * </p>
 * <br>1. 研发人员更关注于业务，可能会忽略掉该类字段的定义。
 * <br>2. 如果在每一个实体类中都去定义可能会出现命名混乱。
 * <br>3. 本身与业务无关，混淆在业务字段之中无疑是画蛇添足，徒增烦恼。
 * <p>
 * 以上便是定义该通用属性类的封装，每一个具体的业务实体只需要继承即可。
 * </p>
 * Created on 2024-01-06 17:35
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, JpaAuditingListener.class})
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id", updatable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    /**
     * 创建人
     */
    @CreatedBy
    @Column(name = "creator", nullable = false, updatable = false, length = 10)
    private Long creator;
    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false, length = 10)
    private Long createTime;
    /**
     * 修改用户
     */
    @LastModifiedBy
    @Column(name = "modifier", insertable = false, length = 10)
    private Long modifier;
    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time", insertable = false, length = 10)
    private Long modifyTime;

    /**
     * 逻辑删除字段
     */
    @Column(name = "tombstone", nullable = false)
    @Enumerated(EnumType.STRING)
    private Delete tombstone = Delete.UNDELETED;


    @Column(name = "version", nullable = false)
    @Version
    private Long version;

}
