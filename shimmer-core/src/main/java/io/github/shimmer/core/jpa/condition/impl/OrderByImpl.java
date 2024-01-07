package io.github.shimmer.core.jpa.condition.impl;

import io.github.shimmer.core.jpa.condition.Condition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;

/**
 * <p>OrderBy 注解转换 Predicate 实现</p>
 *
 * @author yu_haiyang
 */
public class OrderByImpl implements Condition {
    @Override
    public <T> Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, T entity, Field field) throws IllegalAccessException {
        return null;
    }
}
