package io.github.shimmer.core.jpa.wrapper;

import java.io.Serializable;
import java.util.function.Function;

/**
 * <p>传递字段的函数</p>
 * Created on 2024-01-21 15:02
 *
 * @author yu_haiyang
 */
@FunctionalInterface
public interface FieldFunction<T, R> extends Function<T, R>, Serializable {
}
