package io.github.shimmer.core.jpa.repository;

import io.github.shimmer.core.jpa.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>定义JPA扩展实现，基于注解的条件查询接口定义</p>
 * Created on 2024-01-06 07:35
 *
 * @author yu_haiyang
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    List<T> queryWithConditionEntity(T entity);

    List<T> queryWithConditionEntity(T entity, Sort sort);

    Page<T> queryWithConditionEntity(T entity, Pageable pageable);

    Map<ID, T> queryAllToMap();

    T logicDelete(T entity);

    T logicDeleteById(ID id);


}
