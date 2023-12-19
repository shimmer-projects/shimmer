package io.github.shimmer.core.validator;


import io.github.shimmer.utils.Utils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>自定义金额认证</p>
 * Created on 2023-12-19 16:09
 *
 * @author yu_haiyang
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMoney.Validator.class})
public @interface IsMoney {

    boolean required() default true;

    String message() default "金额格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsMoney, BigDecimal> {

        private static final Pattern money_pattern = Pattern.compile("^[0-9]+\\.?[0-9]{0,2}$");
        private boolean required = false;

        public void initialize(IsMoney constraintAnnotation) {
            required = constraintAnnotation.required();
        }

        public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
            if (required) {
                return isMoney(value);
            } else {
                if (value == null) {
                    return true;
                } else {
                    return isMoney(value);
                }
            }
        }

        /**
         * 验证金额0.00
         */
        private boolean isMoney(BigDecimal money) {
            if (Utils.useString(money.toString()).isEmpty()) {
                return false;
            }

            if (money.doubleValue() == 0) {
                return false;
            }
            Matcher m = money_pattern.matcher(String.valueOf(money.doubleValue()));
            return m.matches();
        }

    }

}
