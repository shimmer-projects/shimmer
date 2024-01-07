package io.github.shimmer.core.jpa.repository;

import io.github.shimmer.core.constant.Delete;
import io.github.shimmer.core.jpa.condition.SpecificationBuilder;
import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>实现JPA基于注解的条件查询定义接口的查询逻辑</p>
 * Created on 2024-01-06 07:35
 *
 * @author yu_haiyang
 */

@Slf4j
public class BaseJpaRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseJpaRepository<T, ID> {

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

    }

    public BaseJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<T> queryWithConditionEntity(T entity) {
        SpecificationBuilder<T> specificationBuilder = SpecificationBuilder.<T>builder().entity(entity).build();
        return this.findAll(specificationBuilder);
    }

    @Override
    public List<T> queryWithConditionEntity(T entity, Sort sort) {
        SpecificationBuilder<T> specificationBuilder = SpecificationBuilder.<T>builder().entity(entity).build();
        return this.findAll(specificationBuilder, sort);
    }

    @Override
    public Page<T> queryWithConditionEntity(T entity, Pageable pageable) {
        SpecificationBuilder<T> specificationBuilder = SpecificationBuilder.<T>builder().entity(entity).build();
        return this.findAll(specificationBuilder, pageable);
    }

    @Override
    public Map<ID, T> queryAllToMap() {
        return this.findAll().stream()
                .collect(Collectors.toMap(BaseEntity::getId, entity -> entity));
    }

    @Override
    public T logicDelete(T entity) {
        return this.logicDeleteById(entity.getId());
    }

    @Override
    public T logicDeleteById(ID id) {
        T t = super.findById(id).orElseThrow();
        t.setTombstone(Delete.DELETED);
        t = super.save(t);
        return t;
    }
}
