package io.github.shimmer.utils;

import java.util.Objects;


/**
 * null相关的工具类
 *
 * @param <T> 待操作的数据类型
 *            <br/>
 *            Created on 2023-12-17 10:34
 * @author yu_haiyang
 */
public class Nullables<T> {

    protected T source;

    Nullables() {
    }

    Nullables(T obj) {
        source = obj;
    }

    public boolean isNull() {
        return Objects.isNull(source);
    }

    public boolean isNotNull() {
        return !isNull();
    }

    public T finish() {
        return source;
    }

    @Override
    public String toString() {
        return "Result: " + source;
    }
}
