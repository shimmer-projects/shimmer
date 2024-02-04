package io.github.shimmer.codegen.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 属性定义
 * Created on 2023-02-04 14:13
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "gen_column")
public class GenColumn extends BaseEntity<Long> {

    /**
     * 属性名称, 以驼峰命名， JPA中的通过代码转成下划线
     */
    private String fieldName;

    /**
     * 不填写的情况下，是fieldName转成下划线的方式
     */
    private String columnName;

    /**
     * 属性类型 String Long Integer Enum ...
     */
    private String columnType;

    /**
     * 字段长度
     */
    private Integer length;

    /**
     * 整数位数
     */
    private Integer prec;

    /**
     * 小数位数
     */
    private Integer scale;

    /**
     * 字段说明
     */
    private String comments;

    /**
     * 是否主键
     */
    private boolean pk;

    /**
     * 是否可以为空
     */
    private boolean nullable;

    /**
     * 是否可以修改
     */
    private boolean updatable;

    /**
     * 是否唯一
     */
    private boolean uniq;

    /**
     * 查询类型： Like Equal In ...
     */
    private String searchType;

    /**
     * 参数校验器，包含校验组等
     */
    @Transient
    private List<String> validators;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "model")
    private GenModel model;

}
