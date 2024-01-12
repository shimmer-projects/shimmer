package io.github.shimmer.core.jpa.repository;

import io.github.shimmer.core.jpa.condition.Spec;
import io.github.shimmer.core.jpa.entity.BaseEntity;
import io.github.shimmer.core.jpa.entity.BaseTreeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>定义JPA树结构表的扩展接口的实现</p>
 * Created on 2024-01-06 07:40
 *
 * @author yu_haiyang
 */
@Slf4j
public class BaseTreeJpaRepositoryImpl<T extends BaseTreeEntity<T, ID>, ID extends Serializable>
        extends BaseJpaRepositoryImpl<T, ID>
        implements BaseTreeJpaRepository<T, ID> {

    public BaseTreeJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

    }

    public BaseTreeJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<T> queryTreeByPid(ID pid) {
        List<T> all = super.findAll((Specification<T>) (root, query, criteriaBuilder) -> {
            Path<Object> pidPath = root.get("pid");
            Predicate predicate = Objects.isNull(pid) ? criteriaBuilder.isNull(pidPath) :
                    criteriaBuilder.equal(pidPath, pid);
            Predicate restriction = query.where(predicate).getRestriction();

            return restriction;
        });
        return all;
    }

    @Override
    public List<T> queryTree() {
        return queryTree((Specification<T>) null, null);
    }

    @Override
    public List<T> queryTree(T entity, ID rootId) {
        Spec<T> spec = Spec.<T>builder().entity(entity).build();
        return queryTree(spec, rootId);
    }

    @Override
    public List<T> queryTree(Specification<T> spec, ID rootId) {
        List<T> entityTreeList = new ArrayList<>();

        Map<ID, T> entityMap = super.findAll(spec).stream()
                .collect(Collectors.toMap(BaseEntity::getId, entity -> entity));

        for (Map.Entry<ID, T> entry : entityMap.entrySet()) {
            T node = entry.getValue();
            ID parentId = node.getPid();
            if (parentId == rootId) {
                // 如果是顶层节点，直接添加到结果集合中
                entityTreeList.add(node);
            } else {
                // 如果不是顶层节点，找其父节点，并且添加到父节点的子节点集合中
                T pNode = entityMap.get(parentId);
                if (pNode != null) {
                    pNode.getChildren().add(node);
                }
            }
        }
        return entityTreeList;
    }
}
