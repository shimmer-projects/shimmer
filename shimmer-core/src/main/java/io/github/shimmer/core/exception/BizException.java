package io.github.shimmer.core.exception;


/**
 * <p>通用业务异常</p>
 * Created on 2022-12-18 19:54
 *
 * @author yu_haiyang
 */
public class BizException extends RuntimeException {

    public BizException(String msg) {
        super(msg);
    }
}
