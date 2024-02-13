package io.github.shimmer.core.response;

import io.github.shimmer.core.response.data.ApiResult;
import lombok.Getter;

/**
 * <p>控制器如果返回的是string类型，转换成json格式的中转异常</p>
 * Created on 2024-01-14 17:20
 *
 * @author yu_haiyang
 */
@Getter
public class StringToApiResult extends RuntimeException {

    private final transient ApiResult apiResult;

    public StringToApiResult(ApiResult apiResult) {
        this.apiResult = apiResult;
    }
}
