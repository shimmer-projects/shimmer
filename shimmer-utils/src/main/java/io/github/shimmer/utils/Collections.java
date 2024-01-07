package io.github.shimmer.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * 集合工具类
 *
 * @param <T> 集合中元素的类型
 *            <br/>
 *            Created on 2023-12-17 10:34
 * @author yu_haiyang
 */
public class Collections<T> extends Nullables<List<T>> {

    Collections(List<T> source) {
        super.source = source;
    }


    public <R> Collections<R> map(Function<? super T, ? extends R> mapper) {
        List<R> rs = new ArrayList<>();
        for (T t : source) {
            R apply = mapper.apply(t);
            rs.add(apply);
        }
        return new Collections<>(rs);
    }


    public Collections<T> filter(Predicate<? super T> predicate) {
        List<T> rs = new ArrayList<>();
        for (T t : source) {
            if (predicate.test(t)) {
                rs.add(t);
            }
        }
        return new Collections<>(rs);
    }

    public <R> Collections<R> flatMap(Function<? super T, Collection<? extends R>> mapper) {
        List<R> rs = new ArrayList<>();
        for (T t : source) {
            Collection<? extends R> apply = mapper.apply(t);
            rs.addAll(apply);
        }
        return new Collections<>(rs);
    }

    public Collections<T> sorted() {
        source = source.stream().sorted().toList();
        return this;
    }

    public Collections<T> skip(int n) {
        List<T> rs = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            if (i >= n) {
                rs.add(source.get(i));
            }
        }
        source = rs;
        return this;
    }

    public Collections<T> limit(int maxSize) {
        List<T> rs = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            if (i < maxSize) {
                rs.add(source.get(i));
            }
        }
        source = rs;
        return this;
    }

    public Booleans isEmpty() {
        return isNull() ? new Booleans(true) : new Booleans(source.isEmpty());
    }

    public Booleans isNotEmpty() {
        return isEmpty().reverse();
    }

    public Set<T> toSet() {
        return new HashSet<>(source);
    }

    public List<T> toList() {
        return new ArrayList<>(source);
    }
}
