package io.github.shimmer.core.jpa.condition.impl;

import io.github.shimmer.core.jpa.condition.Condition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * <p>Equal 注解转换 Predicate 实现</p>
 *
 * @author yu_haiyang
 */
public class EqualImpl implements Condition {
    @Override
    public <T> Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, T entity, Field field) throws IllegalAccessException {
        String name = field.getName();
        Object value = field.get(entity);
        if (Objects.nonNull(value)) {
            if (value instanceof String && !StringUtils.hasText(value.toString())) {
                return null;
            }
            return builder.equal(root.get(name), value);
        }
        return null;
    }
}
