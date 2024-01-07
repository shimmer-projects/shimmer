package io.github.shimmer.core.jpa.condition;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 基于注解条件表达式的翻译入口。
 * 可以将标注了既定条件注解的实体翻译成JPA的Specification条件表达式。
 *
 * @author yu_haiyang
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificationBuilder<ENTITY> implements Specification<ENTITY> {

    private ENTITY entity;


    /**
     * @param root    得到查询的根，root.get(“变量名”)，根据变量名查询。
     * @param query   构建查询的顶层规则（where,from等）
     * @param builder 构建查询的底层规则（equal，like，in等）
     * @return 条件语句
     */
    @Override
    public Predicate toPredicate(@NonNull Root<ENTITY> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        // 构建条件语句
        List<Predicate> predicates = new ArrayList<>();
        Map<Integer, Order> orderByMap = new HashMap<>();
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                try {
                    Annotation[] annotations = field.getAnnotations();
                    Condition condition = ConditionFactory.getCondition(annotations);
                    if (Objects.nonNull(condition)) {
                        Predicate predicate = condition.buildPredicate(root, builder, entity, field);
                        if (Objects.nonNull(predicate)) {
                            predicates.add(predicate);
                        }
                    }
//                    // 检测字段是否进行排序使用
                    OrderBy orderBy = field.getAnnotation(OrderBy.class);
                    if (Objects.nonNull(orderBy)) {
                        int order = orderBy.order();
                        if (orderByMap.containsKey(order)) {
                            throw new RuntimeException("OrderBy order must be unique number");
                        }
                        String targetField = orderBy.targetField();
                        targetField = Objects.nonNull(targetField) ? targetField : field.getName();
                        Path<Object> orderPath = root.get(targetField);
                        if (orderBy.sort() == OrderBy.Sort.ASC) {
                            Order asc = builder.asc(orderPath);
                            orderByMap.put(order, asc);
                        } else {
                            Order desc = builder.desc(orderPath);
                            orderByMap.put(order, desc);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        List<Order> orders = new ArrayList<>();
        orderByMap.keySet().stream().sorted().toList().forEach(item -> {
            Order order = orderByMap.get(item);
            orders.add(order);
        });
        // 构建整体的where语句
        return query.where(predicates.toArray(new Predicate[0])).orderBy(orders).getRestriction();
    }
}
