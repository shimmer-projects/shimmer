package io.github.shimmer.core.jpa.condition.impl;

import io.github.shimmer.core.jpa.condition.Condition;
import io.github.shimmer.core.jpa.condition.In;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * <p>In 注解转换 Predicate 实现</p>
 *
 * @author yu_haiyang
 */
public class InImpl implements Condition {
    @Override
    public <T> Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, T entity, Field field) throws IllegalAccessException {

        String name = field.getName();
        Object value = field.get(entity);
        if (Objects.nonNull(value)) {
            if (StringUtils.hasText(value.toString())) {
                CriteriaBuilder.In<Object> build = builder.in(root.get(name));
                In in = field.getAnnotation(In.class);
                String separator = in.separator();
                String[] elements = value.toString().split(separator);
                for (String element : elements) {
                    build.value(element);
                }
                return build;
            }
        }


        return null;
    }
}
