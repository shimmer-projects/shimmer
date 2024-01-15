package io.github.shimmer.core.response.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>通用的返回对象</p>
 * Created on 2023-12-15 20:29
 *
 * @author yu_haiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"code", "desc", "time", "cost", "data"})
public class ApiResult<T> {

    /**
     * 请求响应码，区别于HTTP原始状态码，这个更业务一些。
     */
    private String code;
    /**
     * 当前状态的描述说明信息
     */
    private String desc;

    /**
     * 真正的响应数据
     */
    private T data;
    /**
     * 请求的时间戳
     */
    @Builder.Default
    private final long time = System.currentTimeMillis();

    /**
     * 接口处理耗时
     */
    private long cost;

    public static <T> ApiResult<T> fail(String desc) {
        return fail(ApiCode.ERROR, desc);
    }

    public static <T> ApiResult<T> fail(String code, String desc) {
        return ApiResult.<T>builder().code(code).desc(desc).build();
    }

    public static <T> ApiResult<T> fail(ApiCode code) {
        return ApiResult.<T>builder().code(code.getCode()).desc(code.getMsg()).build();
    }

    public static <T> ApiResult<T> fail(ApiCode code, String desc) {
        return ApiResult.<T>builder().code(code.getCode()).desc(desc).build();
    }

    public static <T> ApiResult<T> ok() {
        return ApiResult.ok(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder().code(ApiCode.OK.getCode()).desc(ApiCode.OK.getMsg()).data(data).build();
    }
}
