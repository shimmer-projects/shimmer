package io.github.shimmer.utils;

import java.util.List;
import java.util.Map;

/**
 * 工具类入口调用类
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public final class Utils {

    public static Strings useString(String str) {
        return new Strings(str);
    }

    public static Regex useRegex(String regex) {
        return new Regex(regex);
    }

    public static <K, V> Maps<K, V> useMap(Map<K, V> map) {
        return new Maps<>(map);
    }

    public static <T> Arrays<T> useArray(T[] array) {
        return new Arrays<>(array);
    }

    public static <T> Collections<T> useCollection(List<T> list) {
        return new Collections<>(list);
    }

    public static Booleans useCollection(Boolean bool) {
        return new Booleans(bool);
    }

    public static <T> Assertable<T> useAssertable(T obj) {
        return new Assertable<>(obj);
    }

    public static Datetime useDatetime(Long date) {
        return new Datetime(date);
    }

    public static Datetime useDatetime(String date) {
        return new Datetime(date);
    }

    public static Datetime useDatetime(String date, String pattern) {
        return new Datetime(date, pattern);
    }

    public static <T> Nullables<T> useNullables(T obj) {
        return new Nullables<>(obj);
    }

    public static Files useFile() {
        return new Files();
    }

    public static Base64s useBase64(String str) {
        return new Base64s(str);
    }

    public static Booleans useBoolean() {
        return new Booleans(false);
    }

    public static <T extends Number> Maths<T> useMath(T number) {
        return new Maths<>(number);
    }

    public static Step useStep(int stepLength) {
        return new Step(stepLength);
    }
}
