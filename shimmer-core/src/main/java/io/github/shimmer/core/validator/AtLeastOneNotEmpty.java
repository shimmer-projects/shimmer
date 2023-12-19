package io.github.shimmer.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * 校验至少一个属性不为空
 * </p>
 * Created on 2023-05-22 11:22
 *
 * @author yu_haiyang
 */
@Documented
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = {AtLeastOneNotEmpty.Validator.class})
public @interface AtLeastOneNotEmpty {

    String[] fields();

    /**
     * 提示信息
     */
    String message() default "存在不可同时为空的字段均为空";

    /**
     * 校验组
     */

    Class<?>[] groups() default {};

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

        private String[] fields;

        @Override
        public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
            this.fields = constraintAnnotation.fields();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            try {
                Class<?> valueClass = value.getClass();
                for (String fieldName : fields) {
                    Field field = valueClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object property = field.get(value);
                    if (property != null && !"".equals(property)) {
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
