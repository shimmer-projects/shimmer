package io.github.shimmer.core.jpa.condition;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;

/**
 * <p>
 * 条件注解翻译成JPA条件语句的接口函数
 * </p>
 *
 * @author yu_haiyang
 */
public interface Condition {

    <T> Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, T entity, Field field) throws IllegalAccessException;
}
