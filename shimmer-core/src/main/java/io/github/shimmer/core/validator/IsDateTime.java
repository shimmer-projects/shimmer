package io.github.shimmer.core.validator;

import io.github.shimmer.utils.Utils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * <p>日期验证</p>
 * Created on 2023-12-19 16:10
 *
 * @author yu_haiyang
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsDateTime.Validator.class}) // 标明由哪个类执行校验逻辑
public @interface IsDateTime {

    // 校验出错时默认返回的消息
    String message() default "日期格式错误";

    // 分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dateFormat() default "yyyy-MM-dd";


    class Validator implements ConstraintValidator<IsDateTime, String> {
        private String dateFormat;

        /**
         * 用于初始化注解上的值到这个validator
         */
        @Override
        public void initialize(IsDateTime constraintAnnotation) {
            dateFormat = constraintAnnotation.dateFormat();
        }

        /**
         * 具体的校验逻辑
         */
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (Utils.useString(value).isBlank()) {
                return true;
            } else {
                return isDateTime(value, dateFormat);
            }
        }

        /**
         * 验证 日期
         */
        private boolean isDateTime(String date, String dateFormat) {
            if (Utils.useString(date).isBlank()) {
                return false;
            }
            try {
                Long ignore = Utils.useDatetime(date, dateFormat).finish();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
