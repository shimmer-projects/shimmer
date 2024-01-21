package io.github.shimmer.core.jpa.wrapper;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * <p>通用实现</p>
 * Created on 2024-01-20 19:27
 *
 * @author yu_haiyang
 */
abstract class AbstractWrapper<T> implements Specification<T> {

    /**
     * 条件字段
     */
    private final List<QueryField> queryFields;

    /**
     * 排序字段
     */
    private final List<OrderBy> orderBy;

    protected AbstractWrapper(List<QueryField> queryFields, List<OrderBy> orderBy) {
        this.queryFields = queryFields;
        this.orderBy = orderBy;
    }

    /**
     * @param root    得到查询的根，root.get(“变量名”)，根据变量名查询。
     * @param query   构建查询的顶层规则（where,from等）
     * @param builder 构建查询的底层规则（equal，like，in等）
     * @return 条件语句
     */
    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        // 1. 实现字段名条件
        // .eq("id", 1)
        // 2. 函数式字段条件
        // .eq(BaseEntity::getId, 1)
        // 3. 注解式条件式
        // @Equal

        Predicate[] predicates = new Predicate[queryFields.size()];
        for (int i = 0; i < queryFields.size(); ++i) {
            predicates[i] = buildPredicate(root, builder, queryFields.get(i));
        }

        if (Objects.equals(predicates.length, 0) && orderBy.isEmpty()) {
            return null;
            // 如果没有order by 直接返回
        } else if (this.orderBy.isEmpty()) {
            return builder.and(predicates);
        } else {
            Order[] orders = new Order[this.orderBy.size()];
            for (int i = 0; i < orderBy.size(); i++) {
                orders[i] = orderBy.get(i).isDesc()
                        ? builder.desc(root.get(orderBy.get(i).getProperty()))
                        : builder.asc(root.get(orderBy.get(i).getProperty()));
            }
            return query.orderBy(orders).where(predicates).getRestriction();
        }
    }

    /**
     * TempPredicate 转换为 Predicate
     */
    private Predicate buildPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, QueryField predicate) {
        switch (predicate.getOperator()) {
            case EQ:
                return criteriaBuilder.equal(root.get(predicate.getFieldName()), predicate.getValue());
            case NE:
                return criteriaBuilder.notEqual(root.get(predicate.getFieldName()), predicate.getValue());
            case GE:
                return criteriaBuilder.ge(root.get(predicate.getFieldName()), (Number) predicate.getValue());
            case GT:
                return criteriaBuilder.gt(root.get(predicate.getFieldName()), (Number) predicate.getValue());
            case LE:
                return criteriaBuilder.le(root.get(predicate.getFieldName()), (Number) predicate.getValue());
            case LT:
                return criteriaBuilder.lt(root.get(predicate.getFieldName()), (Number) predicate.getValue());
            case IN:
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(predicate.getFieldName()));
                Object[] objects = (Object[]) predicate.getValue();
                for (Object obj : objects) {
                    in.value(obj);
                }
                return criteriaBuilder.and(in);
            case NOT_IN:
                return criteriaBuilder.not(root.get(predicate.getFieldName()).in(predicate.getValue()));
            case LIKE:
                return criteriaBuilder.like(root.get(predicate.getFieldName()), "" + predicate.getValue());
            case IS_NULL:
                return criteriaBuilder.isNull(root.get(predicate.getFieldName()));
            case NOT_NULL:
                return criteriaBuilder.isNotNull(root.get(predicate.getFieldName()));
        }
        return null;
    }
}
