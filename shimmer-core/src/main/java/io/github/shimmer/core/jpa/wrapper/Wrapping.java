package io.github.shimmer.core.jpa.wrapper;

/**
 * <p>实体类可以实现的包装转换器</p>
 * Created on 2024-01-22 20:52
 *
 * @author yu_haiyang
 */
public interface Wrapping {

    default <T> QueryWrapper<T> asWrapper() {
        return null;
    }
}
