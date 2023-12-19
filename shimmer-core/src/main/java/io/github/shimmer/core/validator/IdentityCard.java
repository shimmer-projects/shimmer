package io.github.shimmer.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.util.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>身份证校验</p>
 * Created on 2023-12-19 16:10
 *
 * @author yu_haiyang
 */

@Documented
@Constraint(validatedBy = IdentityCard.Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RUNTIME)
public @interface IdentityCard {
    String message() default "身份证内容为空或身份证内容格式错误！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IdentityCard, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return isIdCard(value);
        }

        /**
         * 校验身份证号码
         */
        public static boolean isIdCard(String idCard) {
            return StringUtils.hasText(idCard)
                    && idCard.matches(
                    "[1-9]\\d{5}(18|19|20|(3\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]");
        }
    }

}
