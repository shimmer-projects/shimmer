package io.github.shimmer.core.jpa.condition.impl;

import io.github.shimmer.core.jpa.condition.Condition;
import io.github.shimmer.core.jpa.condition.Like;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * <p>Like 注解转换 Predicate 实现</p>
 *
 * @author yu_haiyang
 */
public class LikeImpl implements Condition {

    private static final String F = "%";

    @Override
    public <T> Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, T entity, Field field) throws IllegalAccessException {
        String name = field.getName();
        Object value = field.get(entity);
        if (Objects.nonNull(value) && StringUtils.hasText(value.toString())) {
            String key;
            Like like = field.getAnnotation(Like.class);
            Like.Mode mode = like.value();
            if (mode == Like.Mode.LEFT) {
                key = value + F;
            } else if (mode == Like.Mode.RIGHT) {
                key = F.concat(value.toString());
            } else {
                key = F.concat(value.toString()).concat(F);
            }
            return builder.like(root.get(name), key);
        }
        return null;
    }
}
