package io.github.shimmer.core.response;

import io.github.shimmer.core.response.data.ApiResult;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p>抽象提取通用响应时间包装到响应结果中</p>
 * Created on 2024-01-14 17:08
 *
 * @author yu_haiyang
 */
public abstract class AbstractCostResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    protected CostAspect costAspect;


    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        Object done = doBeforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);
        if (done instanceof ApiResult<?> apiResult) {
            apiResult.setCost(costAspect.cost());
        }
        return done;
    }

    protected abstract Object doBeforeBodyWrite(Object body,
                                                @NonNull MethodParameter returnType,
                                                @NonNull MediaType selectedContentType,
                                                @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                                @NonNull ServerHttpRequest request,
                                                @NonNull ServerHttpResponse response);
}
