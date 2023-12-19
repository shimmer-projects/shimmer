package io.github.shimmer.core.validator;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


/**
 * <p>初始化validator验证对象</p>
 * Created on 2023-12-19 16:24
 *
 * @author yu_haiyang
 */
@Configuration
public class ValidationConfig {

    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                // failFast在多个条件异常触发时，只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
                .failFast(false)
                .buildValidatorFactory()
                .getValidator();
    }

    //    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }
}
