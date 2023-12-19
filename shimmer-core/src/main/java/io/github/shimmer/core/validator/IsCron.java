package io.github.shimmer.core.validator;


import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.scheduling.support.CronExpression;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>校验是否为合法的 Cron表达式</p>
 * Created on 2023-12-19 16:10
 *
 * @author yu_haiyang
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsCron.Validator.class)
public @interface IsCron {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsCron, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            try {
                return CronExpression.isValidExpression(value);
            } catch (Exception e) {
                return false;
            }
        }
    }
}
