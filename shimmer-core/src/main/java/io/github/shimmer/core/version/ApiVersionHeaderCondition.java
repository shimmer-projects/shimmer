package io.github.shimmer.core.version;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.Objects;

/**
 * <p>请求头控制接口版本：访问时在header带上X-API-VERSION参数即可</p>
 * Created on 2023-12-18 14:32
 *
 * @author yu_haiyang
 */
public class ApiVersionHeaderCondition implements RequestCondition<ApiVersionHeaderCondition> {
    private static final String X_VERSION = "X-API-VERSION";
    private final String version;

    public ApiVersionHeaderCondition(String version) {
        this.version = version;
    }

    @Override
    @NonNull
    public ApiVersionHeaderCondition combine(ApiVersionHeaderCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionHeaderCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionHeaderCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        String headerVersion = httpServletRequest.getHeader(X_VERSION);
        if (Objects.equals(version, headerVersion)) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(@NonNull ApiVersionHeaderCondition apiVersionCondition, @NonNull HttpServletRequest httpServletRequest) {
        return 0;
    }

    public String getApiVersion() {
        return version;
    }

}
