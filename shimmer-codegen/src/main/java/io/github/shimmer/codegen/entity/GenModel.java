package io.github.shimmer.codegen.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 模型； 多模型中定义主模型
 * Created on 2023-02-04 14:48
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "gen_model")
public class GenModel extends BaseEntity<Long> {

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型描述
     */
    private String modelDescription;


    /**
     * 所属模块
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "module")
    private GenModule module;


    /**
     * 包含属性
     */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "model")
    List<GenColumn> columns;
}
