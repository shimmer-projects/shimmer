package io.github.shimmer.codegen.entity;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>多表关联</p>
 * Created on 2023-02-04 14:50
 *
 * @author yu_haiyang
 */

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "gen_ref")
public class GenRef extends BaseEntity<Long> {
}
