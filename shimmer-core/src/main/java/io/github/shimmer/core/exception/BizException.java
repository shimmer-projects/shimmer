package io.github.shimmer.core.exception;


import io.github.shimmer.core.response.data.ApiCode;
import lombok.Getter;

/**
 * <p>通用业务异常</p>
 * Created on 2022-12-18 19:54
 *
 * @author yu_haiyang
 */
@Getter
public class BizException extends RuntimeException {

    private String code = ApiCode.ERROR.getCode();

    public BizException() {
    }
    public BizException(String msg) {
        super(msg);
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
