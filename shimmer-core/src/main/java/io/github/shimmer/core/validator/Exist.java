package io.github.shimmer.core.validator;

import io.github.shimmer.core.constant.Delete;
import io.github.shimmer.core.context.SpringContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * 字段值在数据库中的存在性校验的几种情况：
 * 1. 某一个字段的唯一性
 * 2. 多个字段组合唯一性
 * </p>
 * Created on 2023-12-19 16:10
 *
 * @author yu_haiyang
 */
@Documented
@Repeatable(Exist.List.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = Exist.Validator.class)
public @interface Exist {

    /**
     * JPA对应的实体类
     */
    Class<?> entity();

    /**
     * 参数类属性名
     */
    String[] sourceField();

    /**
     * 实体的属性名，多个字段表示组合唯一性校验
     * 如果目标字段与原字段完全一致，则可以省略，否则，必须保持完全一致的 对应关系
     */
    String[] targetField() default {};

    /**
     * 参数传递过来的属性名
     */
    String sourceId();

    /**
     * 实体的主键名称
     * 如果目标字段与原字段完全一致，则可以省略，否则，必须保持完全一致的 对应关系
     */
    String targetId() default "id";

    /**
     * 逻辑删除标识字段
     */
    String logicDeleteField() default "";

    /**
     * 提示信息
     */
    String message() default "在数据库中已经存在";

    /**
     * 校验组
     */

    Class<?>[] groups() default {};

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @interface List {
        Exist[] value();
    }

    class Validator implements ConstraintValidator<Exist, Object> {

        private Exist exist;

        @Override
        public void initialize(Exist constraintAnnotation) {
            this.exist = constraintAnnotation;
        }

        @SneakyThrows
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            Class<?> entity = exist.entity();
            String sourceId = exist.sourceId();
            String targetId = exist.targetId();
            String[] sourceFields = exist.sourceField();
            String[] targetFields = exist.targetField();
            String logicDeleteField = exist.logicDeleteField();

            EntityManager entityManager = SpringContext.getBean(EntityManager.class);
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();

            Class<?> valueClass = value.getClass();
            Field id = valueClass.getDeclaredField(sourceId);
            id.setAccessible(true);
            Object idValue = id.get(value);
            CriteriaQuery<?> query = builder.createQuery(entity);
            Root<?> root = query.from(entity);

            java.util.List<Predicate> predicates = new ArrayList<>();

            // 主键排除
            if (Objects.nonNull(idValue)) {
                Path<Object> idPath = root.get(targetId);
                predicates.add(builder.notEqual(idPath, idValue));
            }
            // 校验字段
            for (int i = 0; i < sourceFields.length; i++) {
                String sourceField = sourceFields[i];
                String targetField = sourceField;
                if (targetFields.length == sourceFields.length) {
                    targetField = targetFields[i];
                }
                Path<Object> path = root.get(targetField);
                Field sf = valueClass.getDeclaredField(sourceField);
                sf.setAccessible(true);
                Object sourceValue = sf.get(value);
                predicates.add(builder.equal(path, sourceValue));
            }

            // 排除逻辑删除数据
            if (StringUtils.hasText(logicDeleteField)) {
                Path<Object> logicDeleteFieldPath = root.get(logicDeleteField);
                predicates.add(builder.equal(logicDeleteFieldPath, Delete.UNDELETED.name()));
            }
            query.where(predicates.toArray(new Predicate[0]));
            java.util.List<?> resultList = entityManager.createQuery(query).setMaxResults(1).getResultList();

            return resultList.size() == 0;
        }
    }

}
