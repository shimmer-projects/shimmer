package io.github.shimmer.core.data;

/**
 * <p>
 * 业务状态码，用于请求响应中描述相应业务的处理情况，与HttpStatus类似，扩展业务
 * </p>
 * Created on 2023-12-15 20:40
 *
 * @author yu_haiyang
 */
public enum BizStatus {
    /**
     * 成功的状态
     */
    SUCCESS,
    /**
     * 失败的状态
     */
    ERROR,

    /**
     * 验证失败
     */
    VALIDATION_FAILED
}
