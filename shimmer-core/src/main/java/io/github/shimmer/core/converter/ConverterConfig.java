package io.github.shimmer.core.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>自定义转换器装载配置</p>
 * <p>
 * Created on 2023-12-17 20:21
 *
 * @author yu_haiyang
 */
@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    /**
     * 枚举类的转换器工厂 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IntegerToEnumConverterFactory());
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new StringTimeToDateConverter());
        registry.addConverter(new StringTimeToLocalDateConverter());
        registry.addConverter(new StringTimeToLocalTimeConverter());
        registry.addConverter(new StringTimeToLocalDateTimeConverter());
    }

}
