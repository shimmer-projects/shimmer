package io.github.shimmer.core.response;

import io.github.shimmer.core.response.data.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.reflect.Method;

/**
 * <p>控制器如果返回的是string类型，转换成json格式的包装器</p>
 * Created on 2024-01-14 17:19
 *
 * @author yu_haiyang
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StringResponseBodyAdvice extends AbstractCostResponseBodyAdvice {

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();
        assert method != null;
        Class<?> type = method.getReturnType();
        return type.equals(String.class);
    }

    @Override
    protected Object doBeforeBodyWrite(Object body,
                                       @NonNull MethodParameter returnType,
                                       @NonNull MediaType selectedContentType,
                                       @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                       @NonNull ServerHttpRequest request,
                                       @NonNull ServerHttpResponse response) {
        // 如果返回的是字符串特殊处理
        // 过滤String类型，String单独序列化方式
        // 方式1：将StringHttpMessageConverter从配置中移除 (不推荐)
        // 方式2: 契合下StringHttpMessageConverter，封装ApiResult，然后将ApiResult进行Json字符串的转换
        ApiResult<Object> apiResult = ApiResult.ok(body);
        apiResult.setCost(costAspect.cost());
        // return objectMapper.writeValueAsString(apiResult);
        // 使用json序列化为字符串之后，虽然返回的是json格式，但是响应的content-type 不是application/json，而是text/html.因此通过异常的形式进行一次转换
        throw new StringToApiResult(apiResult);
    }
}
