package io.github.shimmer.core.debounce;


import io.github.shimmer.core.exception.annotation.ExceptionMapper;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * 接口防抖标识注解
 * </p>
 * Created on 2022-12-18 19:39
 *
 * @author yu_haiyang
 */
@ExceptionMapper(httpStatus = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, msg = "请求过于频繁!")
public class LimitAccessException extends RuntimeException {
    public LimitAccessException(String message) {
        super(message);
    }
}
