package io.github.shimmer.core.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * BaseEntity类中定义类审计类的通用字段，但是还有一种树结构数据的实体定义，数据通常会有一个父级ID的属性，
 * 同时也有一个不需要持久化的承载子节点的集合。
 * 定义该类的初衷主要是以下几个原因：
 * <br>
 * 1. 统一树形结构数据表的定义，避免每个实体类自行定义，字段名称不统一。
 * <br>
 * 2. 实现一个JpaRepository的扩展，一种支持查询树形结构数据的实现。将树形结构的查询逻辑统一起来，无需业务人员单独将一个列表的数据处理成树形结构。
 * </p>
 * Created on 2024-01-06 17:35
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseTreeEntity<T, ID extends Serializable> extends BaseEntity<ID> {

    /**
     * 父级id
     */
    @Column(name = "pid")
    private ID pid;


    @Transient
    private List<T> children = new ArrayList<>();
}
