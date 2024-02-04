package io.github.shimmer.codegen.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * <p>模块定义</p>
 * Created on 2023-02-04 14:07
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "gen_module")
public class GenModule extends BaseEntity<Long> {

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "package_name")

    private String packageName;

    @Column(name = "author")

    private String author;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "module")
    private List<GenModel> models;
}
