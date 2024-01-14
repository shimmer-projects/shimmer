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
@JsonPropertyOrder({"code", "msg", "utc8", "cost", "data"})
public class ApiResult<T> {

    /**
     * 请求响应码，区别于HTTP原始状态码，这个更业务一些。
     */
    private String code;
    /**
     * 当前状态的描述说明信息
     */
    private String msg;

    /**
     * 真正的响应数据
     */
    private T data;
    /**
     * 请求的时间戳
     */
    @Builder.Default
    private Long utc8 = System.currentTimeMillis();

    /**
     * 接口处理耗时
     */
    private Long cost;

    public static <T> ApiResult<T> fail(String msg) {
        return fail(ApiCode.ERROR, msg);
    }

    public static <T> ApiResult<T> fail(String code, String msg) {
        return ApiResult.<T>builder().code(code).msg(msg).build();
    }

    public static <T> ApiResult<T> fail(ApiCode code) {
        return ApiResult.<T>builder().code(code.getCode()).msg(code.getMsg()).build();
    }

    public static <T> ApiResult<T> fail(ApiCode code, String msg) {
        return ApiResult.<T>builder().code(code.getCode()).msg(msg).build();
    }

    public static <T> ApiResult<T> ok() {
        return ApiResult.ok(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder().code(ApiCode.OK.getCode()).msg(ApiCode.OK.getMsg()).data(data).build();
    }
}
