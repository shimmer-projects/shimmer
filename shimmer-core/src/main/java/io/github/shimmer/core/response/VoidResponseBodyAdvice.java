package io.github.shimmer.core.response;

import io.github.shimmer.core.response.data.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * <p>空返回值的拦截处理</p>
 * Created on 2024-01-10 17:22
 *
 * @author yu_haiyang
 */
@ControllerAdvice
@Order(value = 1000)
public class VoidResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 只处理返回空的Controller方法.
     *
     * @param methodParameter 返回类型
     * @param clazz           消息转换器
     * @return 是否对这种返回值进行处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> clazz) {
        return Objects.requireNonNull(methodParameter.getMethod()).getReturnType().equals(Void.TYPE)
                && !methodParameter.hasMethodAnnotation(NoResponseWrapper.class)
                && !methodParameter.getDeclaringClass().isAnnotationPresent(NoResponseWrapper.class)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(clazz);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        return ApiResult.ok();
    }
}
