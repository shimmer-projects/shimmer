package io.github.shimmer.core.version;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * <p>
 * 注入具体的请求实现
 * </p>
 * Created on 2023-12-18 14:29
 *
 * @author yu_haiyang
 */
@Configuration
public class VersionConfig implements WebMvcRegistrations {


    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new PathVersionHandlerMapping();
    }
}
