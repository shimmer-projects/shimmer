package io.github.shimmer.core.jpa.WrapperBuilder;

/**
 * <p>基础WrapperBuilder构建器</p>
 * Created on 2024-01-22 21:28
 *
 * @author yu_haiyang
 */
public abstract class WrapperBuilder<T> {

    public WrapperBuilder<T> eq(String fieldName, Object value) {
        return null;
    }


    public WrapperBuilder<T> ne(String fieldName, Object value) {
        return null;
    }


    public WrapperBuilder<T> gt(String fieldName, Comparable<?> value) {
        return null;
    }


    public WrapperBuilder<T> ge(String fieldName, Comparable<?> value) {
        return null;
    }


    public WrapperBuilder<T> lt(String fieldName, Comparable<?> value) {
        return null;
    }


    public WrapperBuilder<T> le(String fieldName, Comparable<?> value) {
        return null;
    }


    public WrapperBuilder<T> like(String fieldName, String value) {
        return null;
    }


    public WrapperBuilder<T> likeRight(String fieldName, String value) {
        return null;
    }


    public WrapperBuilder<T> likeLeft(String fieldName, String value) {
        return null;
    }


    public WrapperBuilder<T> between(String fieldName, Object min, Object max) {
        return null;
    }


    public WrapperBuilder<T> in(String fieldName, Iterable<?> values) {
        return null;
    }


    public WrapperBuilder<T> in(String fieldName, Object... values) {
        return null;
    }


    public WrapperBuilder<T> notIn(String fieldName, Object... values) {
        return null;
    }


    public WrapperBuilder<T> notIn(String fieldName, Iterable<?> values) {
        return null;
    }


    public WrapperBuilder<T> isNull(String fieldName) {
        return null;
    }


    public WrapperBuilder<T> isNotNull(String fieldName) {
        return null;
    }


    public WrapperBuilder<T> orderBy(String fieldName, boolean desc) {
        return null;
    }
}
