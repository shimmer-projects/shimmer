package io.github.shimmer.core.debounce;


/**
 * <p>
 * 接口防抖标识注解
 * </p>
 * Created on 2022-12-18 19:39
 *
 * @author yu_haiyang
 */
public class LimitAccessException extends RuntimeException {
    public LimitAccessException(String message) {
        super(message);
    }
}
