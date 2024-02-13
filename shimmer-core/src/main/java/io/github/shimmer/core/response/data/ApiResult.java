package io.github.shimmer.core.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "ApiResult", description = "统一响应包装体")
@JsonInclude(JsonInclude.Include.NON_NULL) // 使用JsonInclude注解指明只包含非null的属性，即排除值为null的属性
@JsonPropertyOrder({"code", "desc", "time", "cost", "page", "data"})
public class ApiResult {

    /**
     * 请求响应码，区别于HTTP原始状态码，这个更业务一些。
     */
    @Schema(description = "当前响应的自定义响应编码")
    private String code;
    /**
     * 当前状态的描述说明信息
     */
    @Schema(description = "当前响应的描述说明信息")
    private String desc;
    /**
     * 请求的时间戳
     */
    @Builder.Default
    @Schema(description = "请求时间")
    private final long time = System.currentTimeMillis();

    /**
     * 接口处理耗时
     */
    @Schema(description = "接口耗时")
    private long cost;

    @Schema(description = "分页信息")
    private Pager page;
    /**
     * 真正的响应数据
     */
    @Schema(description = "响应数据")
    private Object data;

    public static ApiResult fail(String desc) {
        return fail(ApiCode.ERROR, desc);
    }

    public static ApiResult fail(String code, String desc) {
        return ApiResult.builder().code(code).desc(desc).build();
    }

    public static ApiResult fail(ApiCode code) {
        return ApiResult.builder().code(code.getCode()).desc(code.getMsg()).build();
    }

    public static ApiResult fail(ApiCode code, String desc) {
        return ApiResult.builder().code(code.getCode()).desc(desc).build();
    }

    public static ApiResult ok() {
        return ApiResult.ok(null);
    }

    public static ApiResult ok(Object data) {
        return ApiResult.builder().code(ApiCode.OK.getCode()).desc(ApiCode.OK.getMsg()).data(data).build();
    }
}
