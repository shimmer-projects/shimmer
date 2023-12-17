package io.github.shimmer.utils;

import java.util.Collection;


/**
 * 断言
 *
 * @param <T> 需要断言的数据类型
 *            <br/>
 *            Created on 2023-12-17 10:34
 * @author yu_haiyang
 */
public class Assertable<T> extends Nullables<T> {

    Assertable(T source) {
        super.source = source;
    }

    public boolean isArray() {
        if (isNotNull()) {
            if (source instanceof Class<?>) {
                return ((Class<?>) source).isArray();
            } else {
                return source.getClass().isArray();
            }
        }
        return false;
    }


    public boolean isCollection() {
        if (isNotNull()) {
            if (source instanceof Class<?>) {
                Collection.class.isAssignableFrom((Class<?>) source);
            } else {
                return Collection.class.isAssignableFrom(source.getClass());
            }
        }
        return false;
    }
}
