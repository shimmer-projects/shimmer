package io.github.shimmer.core.validator;

import io.github.shimmer.core.constant.BaseEnum;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证参数是否存在于枚举类中
 * Created on 2023-12-19 13:53
 *
 * @author yu_haiyang
 */
@Documented
@Inherited
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {Enumerable.Validator.class})
public @interface Enumerable {

    /**
     * 允许的枚举
     */
    Class<? extends BaseEnum<?>> enumClass();

    /**
     * 采用什么字段进行验证
     */
    Type type() default Type.STRING;

    /**
     * 提示信息
     */
    String message() default "该内容已经存在";

    /**
     * 校验组
     */

    Class<?>[] groups() default {};

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Enumerable, String> {

        private Class<? extends BaseEnum<?>> enumClass;

        private Type type;

        @Override
        public void initialize(Enumerable constraintAnnotation) {
            this.enumClass = constraintAnnotation.enumClass();
            this.type = constraintAnnotation.type();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || "".equals(value)) {
                return true;
            }

            BaseEnum<?>[] enums = enumClass.getEnumConstants();
            if (enums == null || enums.length == 0) {
                return false;
            }

            var res = switch (type) {
                case CODE -> Arrays.stream(enums).anyMatch(item -> value.equals(item.getCode()));
                case LABEL -> Arrays.stream(enums).anyMatch(item -> value.equals(item.getLabel()));
                case STRING -> Arrays.stream(enums).anyMatch(item -> value.equals(item.getClass().getName()));
                case ORDINAL -> {
                    // 判断类型是不是数值类型并且是整数
                    // 最小0 最大 length - 1
                    int v = Integer.parseInt(value);
                    yield v >= 0 && v < enums.length;
                }
            };
            return res;
        }
    }

    enum Type {
        ORDINAL,
        STRING,
        CODE,
        LABEL
    }
}
