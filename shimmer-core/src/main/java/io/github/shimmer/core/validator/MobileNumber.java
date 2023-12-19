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
import java.util.regex.Pattern;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>手机号验证器</p>
 * Created on 2023-12-19 16:09
 *
 * @author yu_haiyang
 */
@Documented
@Constraint(validatedBy = MobileNumber.Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RUNTIME)
public @interface MobileNumber {

    /**
     * 设置手机号为空的时候，是否允许验证通过
     */
    boolean allowNull() default true;

    String message() default "手机号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public static class Validator implements ConstraintValidator<MobileNumber, String> {

        private static final Pattern PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

        private MobileNumber mobileNumber;

        @Override
        public void initialize(MobileNumber constraintAnnotation) {
            this.mobileNumber = constraintAnnotation;
        }

        @Override
        public boolean isValid(String iphone, ConstraintValidatorContext context) {
            if (StringUtils.hasText(iphone)) {
                return PATTERN.matcher(iphone).matches();
            }
            // 如果没有传手机号的情况下，默认是符合格式，允许验证通过的
            return mobileNumber.allowNull();
        }
    }
}
