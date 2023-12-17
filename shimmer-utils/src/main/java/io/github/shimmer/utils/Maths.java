package io.github.shimmer.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * 数学计算相关工具类
 *
 * @param <T> Number 子类
 *            <br/>
 *            Created on 2023-12-17 10:34
 * @author yu_haiyang
 */
public class Maths<T extends Number> {

    protected BigDecimal source;

    Maths(T number) {
        source = BigDecimal.valueOf(number.doubleValue());
    }


    public Maths<T> add(int... values) {
        for (int value : values) {
            source = source.add(BigDecimal.valueOf(value));
        }
        return this;
    }

    public Maths<T> add(double... values) {
        for (double value : values) {
            source = source.add(BigDecimal.valueOf(value));
        }
        return this;
    }

    public Maths<T> add(float... values) {
        for (float value : values) {
            source = source.add(BigDecimal.valueOf(value));
        }
        return this;
    }

    public Maths<T> add(long... values) {
        for (long value : values) {
            source = source.add(BigDecimal.valueOf(value));
        }
        return this;
    }

    /**
     * 加
     *
     * @param values 加数，也是BigDecimal类型
     * @return 返回Maths对象自身
     */
    public Maths<T> add(BigDecimal... values) {
        for (BigDecimal value : values) {
            source = source.add(value);
        }
        return this;
    }

    public Maths<T> sub(int... values) {
        for (int value : values) {
            BigDecimal decimal = BigDecimal.valueOf(value);
            source = source.subtract(decimal);
        }
        return this;
    }

    public Maths<T> sub(double... values) {
        for (double value : values) {
            BigDecimal decimal = BigDecimal.valueOf(value);
            source = source.subtract(decimal);
        }
        return this;
    }

    public Maths<T> sub(float... values) {
        for (float value : values) {
            BigDecimal decimal = BigDecimal.valueOf(value);
            source = source.subtract(decimal);
        }
        return this;
    }

    public Maths<T> sub(long... values) {
        for (long value : values) {
            BigDecimal decimal = BigDecimal.valueOf(value);
            source = source.subtract(decimal);
        }
        return this;
    }

    /**
     * 减法
     *
     * @param values 减数
     * @return Maths
     */
    public Maths<T> sub(BigDecimal... values) {
        for (BigDecimal value : values) {
            source = source.subtract(value);
        }
        return this;
    }


    public Maths<T> multiply(int value) {
        multiply(BigDecimal.valueOf(value));
        return this;
    }

    public Maths<T> multiply(double value) {
        multiply(BigDecimal.valueOf(value));
        return this;
    }

    public Maths<T> multiply(float value) {
        multiply(BigDecimal.valueOf(value));
        return this;
    }

    public Maths<T> multiply(long value) {
        multiply(BigDecimal.valueOf(value));
        return this;
    }

    /**
     * 乘法
     *
     * @param value 乘数
     * @return NumberTool
     */
    public Maths<T> multiply(BigDecimal value) {
        source = source.multiply(value);
        return this;
    }


    public Maths<T> divide(int value) {
        divide(BigDecimal.valueOf(value));
        return this;
    }

    public Maths<T> divide(double value) {
        divide(BigDecimal.valueOf(value));
        return this;
    }

    public Maths<T> divide(float value) {
        divide(BigDecimal.valueOf(value));
        return this;
    }

    public Maths<T> divide(long value) {
        divide(BigDecimal.valueOf(value));
        return this;
    }

    /**
     * 除法
     *
     * @param value 除数
     * @return Maths
     */
    public Maths<T> divide(BigDecimal value) {
        source = source.divide(value, RoundingMode.FLOOR);
        return this;
    }

    public int intValue() {
        return source.intValue();
    }

    public long longValue() {
        return source.longValue();
    }

    public double doubleValue() {
        return source.doubleValue();
    }

    public float floatValue() {
        return source.floatValue();
    }

    public String stringValue() {
        return source.toPlainString();
    }
}
