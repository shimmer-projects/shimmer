package io.github.shimmer.core.apiswitch;

import io.github.shimmer.core.exception.annotation.ExceptionMapper;
import org.springframework.http.HttpStatus;

/**
 * <p>接口切换异常</p>
 * Created on 2024-01-19 13:13
 *
 * @author yu_haiyang
 */
@ExceptionMapper(httpStatus = HttpStatus.FORBIDDEN, code = "403", msg = "接口维护中")
public class ApiSwitchException extends RuntimeException {

    public ApiSwitchException() {
    }

    public ApiSwitchException(String msg) {
        super(msg);
    }
}
