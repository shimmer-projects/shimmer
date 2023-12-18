package io.github.shimmer.core.exception;


/**
 * <p>缓存异常，后续对缓存实现的时候使用该异常统一处理</p>
 * Created on 2022-12-18 19:55
 *
 * @author yu_haiyang
 */
public class CacheException extends RuntimeException {
    public CacheException(String message) {
        super(message);
    }

}
