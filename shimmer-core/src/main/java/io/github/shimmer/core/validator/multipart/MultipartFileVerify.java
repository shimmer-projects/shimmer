package io.github.shimmer.core.validator.multipart;

import io.github.shimmer.core.validator.multipart.constant.FileType;
import io.github.shimmer.core.validator.multipart.resolver.MultipartFileValidatorResolver;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 基于 spring validated 框架的文件上传校验注解；
 * 校验 MultipartFile 的文件类型是否符合要求；
 * 目前场景只在 form 表单中进行文件上传，因此 Target 只使用 PARAMETER；
 * 关于转发时用的 JsonObject 形式，使用的并不是 MultipartFile 类，因此无需标记上 FIELD；
 **/
@Inherited
@Target({PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultipartFileVerify.MultipartFilesValidator.class, MultipartFileVerify.MultipartFileValidator.class})
public @interface MultipartFileVerify {

    String message() default "文件校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 文件类型限制
     */
    FileType[] value() default {};

    /**
     * 不允许上传的文件类型，不指定则不作限制
     */
    FileType[] notAllow() default {};

    /**
     * 文件大小限制，小于 0 表示不作限制；单位：千字节（KB）
     */
    long maxSize() default -1L;

    /**
     * 单个 MultipartFile 校验
     */
    class MultipartFileValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile> {
        private MultipartFileVerify multipartFileValid;

        @Override
        public void initialize(MultipartFileVerify constraintAnnotation) {
            this.multipartFileValid = constraintAnnotation;
        }

        @Override
        public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            return MultipartFileValidatorResolver.isFileValid(multipartFileValid, value);
        }
    }

    /**
     * 兼容多 MultipartFile 校验
     */
    class MultipartFilesValidator implements ConstraintValidator<MultipartFileVerify, MultipartFile[]> {

        private MultipartFileVerify multipartFileValid;

        @Override
        public void initialize(MultipartFileVerify constraintAnnotation) {
            this.multipartFileValid = constraintAnnotation;
        }

        @Override
        public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
            // 不做非空验证，非空放行
            if (value == null || value.length == 0) {
                return true;
            }
            for (MultipartFile multipartFile : value) {
                if (!MultipartFileValidatorResolver.isFileValid(multipartFileValid, multipartFile)) {
                    return false;
                }
            }
            return true;
        }
    }

}


