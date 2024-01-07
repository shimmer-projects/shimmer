package io.github.shimmer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * map 工具类
 *
 * @param <K> key的类型
 * @param <V> value的类型
 *            <br/>
 *            Created on 2023-12-17 10:34
 * @author yu_haiyang
 */
public class Maps<K, V> extends Nullables<Map<K, V>> {

    Maps(Map<K, V> map) {
        super.source = map;
    }

    public V get(K key) {
        return source.get(key);
    }


    public V get(K key, V defaultValue) {
        V v = source.get(key);
        return Objects.isNull(v) ? defaultValue : v;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(K key, Class<T> clazz) {

        if (isNotNull()) {
            return (T) (super.source.get(key));
        }
        return null;
    }

    public Maps<K, V> put(K key, V value) {
        if (isNull()) {
            super.source = new HashMap<>();
        }
        super.source.put(key, value);
        return this;
    }

    public Booleans isEmpty() {
        return isNull() ? new Booleans(true) : new Booleans(source.isEmpty());
    }

    public Booleans isNotEmpty() {
        return isEmpty().reverse();
    }
}
