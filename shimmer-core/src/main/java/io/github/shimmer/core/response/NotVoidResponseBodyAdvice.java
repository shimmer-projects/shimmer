package io.github.shimmer.core.response;

import io.github.shimmer.core.ShimmerCoreProperties;
import io.github.shimmer.core.response.data.ApiCode;
import io.github.shimmer.core.response.data.ApiResult;
import io.github.shimmer.core.response.data.Pager;
import io.github.shimmer.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 响应结果包装类
 *
 * @author yu_haiyang
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotVoidResponseBodyAdvice extends AbstractCostResponseBodyAdvice {

    /**
     * 模块的配置信息
     */
    @Resource
    private ShimmerCoreProperties properties;

    /**
     * 路径过滤器
     */
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public boolean supports(MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> clazz) {

        Class<?> responseClass = methodParameter.getParameterType();
        if (responseClass.equals(ResponseEntity.class)) {
            log.debug("Response:响应结果为ResponseEntity，跳过。");
            return false;
        }
//        if (responseClass.equals(ApiResult.class)) {
//            log.debug("Response:响应结果已经是ApiResult，跳过。");
//            return false;
//        }

        Class<?> superClass = methodParameter.getDeclaringClass();
        Method method = methodParameter.getMethod();
        assert method != null;

        // 有@NoResponseWrapper注解修饰的，也跳过
        if (method.isAnnotationPresent(NoResponseWrapper.class) || superClass.isAnnotationPresent(NoResponseWrapper.class)) {
            if (log.isDebugEnabled()) {
                log.debug("Response:方法被@NoResponseWrapper注解修饰，跳过:methodName={}", method.getName());
            }
            return false;
        }

        // 配置了例外包路径，则该路径下的controller都不再处理
        List<String> noResponseWrapperPackages = properties.getNoResponseWrapperPackages();
        if (!CollectionUtils.isEmpty(noResponseWrapperPackages)) {
            // 获取请求所在类的的包名
            String packageName = method.getDeclaringClass().getPackage().getName();
            if (noResponseWrapperPackages.stream().anyMatch(item -> ANT_PATH_MATCHER.match(item, packageName))) {
                log.debug("Response:匹配到NoResponseWrapperPackages例外配置，跳过:packageName={},", packageName);
                return false;
            }
        }

        // 如果返回的是String类型的, method为空、返回值为void、非JSON，直接跳过
        if (method.getReturnType().equals(String.class)
                || method.getReturnType().equals(Void.TYPE)
                || !MappingJackson2HttpMessageConverter.class.isAssignableFrom(clazz)) {
            log.debug("Response:method为空、返回值为void、非JSON，跳过");
            return false;
        }

        log.debug("Response:非空返回值，需要进行封装");
        return true;
    }

    @Override
    protected Object doBeforeBodyWrite(Object body, @NonNull MethodParameter returnType,
                                       @NonNull MediaType selectedContentType,
                                       @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                       @NonNull ServerHttpRequest request,
                                       @NonNull ServerHttpResponse response) {

        log.debug("开始结果封装");

        if (Utils.useNullables(body).isNull()) {
            return ApiResult.ok();
        }

        // 跳过白名单中不需要包装的接口
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        HttpServletRequest servletRequest = servletServerHttpRequest.getServletRequest();
        String servletPath = servletRequest.getServletPath();
        if (log.isDebugEnabled()) {
            String path = servletServerHttpRequest.getURI().getPath();
            log.debug("Response:非空返回值，执行封装:path={}", path);
        }

        // 排除路径白名单
        boolean match = properties.getNoResponseWrapperPackages().stream().anyMatch(path -> ANT_PATH_MATCHER.match(path, servletPath));
        if (match) {
            return body;
        }
        if (body instanceof ApiResult) {
            return body;
        }

        ApiResult.ApiResultBuilder result = ApiResult.builder()
                .code(ApiCode.OK.getCode())
                .desc("success")
                .time(System.currentTimeMillis())
                .data(body);
        if (body instanceof Pager pager) {
            result = result.page(pager).data(pager.getData());
        }
        log.debug("完成结果值封装");
        return result.build();
    }
}
