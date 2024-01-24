package io.github.shimmer.core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

import java.io.IOException;

/**
 * <p>跨域设置</p>
 * Created on 2024-01-24 10:03
 *
 * @author yu_haiyang
 */
//@WebFilter(filterName = "shimmerCorsFilter", urlPatterns = "/*") // 这种方式没法指定优先级
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) rep;
        addCorsResponseHeader(response);
        chain.doFilter(req, rep);
    }

    private void addCorsResponseHeader(HttpServletResponse response) {
        for (CorsHeaderEnum value : CorsHeaderEnum.values()) {
            response.setHeader(value.key, value.getValue());
        }
    }

    @Getter
    private enum CorsHeaderEnum {
        /**
         * 允许所有远程访问
         */
        CORS_ORIGIN("Access-Control-Allow-Origin", "*"),
        /**
         * 允许认证
         */
        CORS_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
        /**
         * 允许远程调用的请求类型
         */
        CORS_METHODS("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT"),
        /**
         * 指定本次预检请求的有效期，单位是秒
         */
        CORS_MAX_AGE("Access-Control-Max-Age", "3600"),
        /**
         * 允许所有请求头
         */
        CORS_HEADERS("Access-Control-Allow-Headers", "*");

        CorsHeaderEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private final String key;
        private final String value;

    }
}
