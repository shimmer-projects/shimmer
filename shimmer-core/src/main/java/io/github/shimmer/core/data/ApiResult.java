package io.github.shimmer.core.data;

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
@JsonPropertyOrder({"code", "msg", "utc8", "data"})
public class ApiResult<T> {

    /**
     * 请求响应码，区别于HTTP原始状态码，这个更业务一些。
     */
    private BizStatus code;
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

    public static <T> ApiResult<T> fail(String msg) {
        return fail(BizStatus.ERROR, msg);
    }

    public static <T> ApiResult<T> fail(BizStatus code, String msg) {
        return ApiResult.<T>builder().code(code).msg(msg).build();
    }

    public static <T> ApiResult<T> ok() {
        return ApiResult.ok(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder().code(BizStatus.SUCCESS).msg("success").data(data).build();
    }
}
