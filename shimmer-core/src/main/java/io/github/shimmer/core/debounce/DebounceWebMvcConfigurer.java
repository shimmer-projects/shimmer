package io.github.shimmer.core.debounce;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * <p>
 * 接口防抖拦截器注入配置类
 * </p>
 * Created on 2022-12-18 19:40
 *
 * @author yu_haiyang
 */
@Slf4j
@Configuration
public class DebounceWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private DebounceInterceptor debounceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(debounceInterceptor)
                // 需拦截的URI配置
                .addPathPatterns("/**")
                // 不需拦截的URI配置
                .excludePathPatterns("/swagger/**", "/static/**", "/resource/**");

    }
}
