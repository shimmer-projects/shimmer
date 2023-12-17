package io.github.shimmer.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.function.Function;


/**
 * 数组工具类
 *
 * @param <T> 数组中数据的类型
 *            <br/>
 *            Created on 2023-12-17 10:34
 * @author yu_haiyang
 */
public class Arrays<T> extends Nullables<T[]> {

    Arrays(T[] source) {
        super.source = source;
    }

    public <R> Arrays<R> map(Function<T, R> function, Class<R> clazz) {

        @SuppressWarnings("unchecked")
        R[] rs = (R[]) Array.newInstance(clazz, source.length);
        for (int i = 0; i < source.length; i++) {
            R apply = function.apply(source[i]);
            rs[i] = apply;
        }
        return new Arrays<>(rs);
    }

    public Strings join(CharSequence seq) {
        StringJoiner joiner = new StringJoiner(seq);
        for (T t : source) {
            joiner.add(t.toString());
        }
        return new Strings(joiner.toString());
    }

    public Collections<T> toCollections() {
        return new Collections<>(new ArrayList<>(java.util.Arrays.asList(source)));
    }

}
