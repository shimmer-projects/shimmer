package io.github.shimmer.core.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * <p>filter注册配置类</p>
 * Created on 2024-01-24 10:26
 *
 * @author yu_haiyang
 */
//@Configuration
//@ServletComponentScan(value = {"io.github.shimmer.core.filter"})
public class FilterConfig {

    /**
     * 注入 corsFilter
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("shimmerCorsFilter");
        //设置优先级别
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
