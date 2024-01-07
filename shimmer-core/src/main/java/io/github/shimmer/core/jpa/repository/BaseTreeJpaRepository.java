package io.github.shimmer.core.jpa.repository;

import io.github.shimmer.core.jpa.entity.BaseTreeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * <p>定义JPA树结构表的扩展接口</p>
 * Created on 2024-01-06 07:40
 *
 * @author yu_haiyang
 */
@NoRepositoryBean
public interface BaseTreeJpaRepository<T extends BaseTreeEntity<T, ID>, ID extends Serializable>
        extends BaseJpaRepository<T, ID> {

    List<T> queryTreeByPid(ID pid);

    List<T> queryTree();

    List<T> queryTree(T entity, ID rootId);

    List<T> queryTree(Specification<T> spec, ID rootId);
}
