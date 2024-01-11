package io.github.shimmer.core.exception;


import io.github.shimmer.core.exception.annotation.ExceptionMapper;
import org.springframework.http.HttpStatus;

/**
 * <p>缓存异常，后续对缓存实现的时候使用该异常统一处理</p>
 * Created on 2022-12-18 19:55
 *
 * @author yu_haiyang
 */

@ExceptionMapper(httpStatus = HttpStatus.INTERNAL_SERVER_ERROR, msg = "缓存异常")
public class CacheException extends RuntimeException {

    public CacheException() {
    }
    public CacheException(String message) {
        super(message);
    }

}
