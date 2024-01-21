package io.github.shimmer.core.jpa.wrapper;

import io.github.shimmer.utils.Utils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>在JPA中实现mybatis plus的类似API</p>
 * Created on 2024-01-20 16:53
 *
 * @author yu_haiyang
 */
public class QueryWrapper<T> extends AbstractWrapper<T> {

    private QueryWrapper(List<QueryField> queryFields, List<OrderBy> orderBy) {
        super(queryFields, orderBy);
    }

    public static <K> Lambda<K> lambda() {
        return new Lambda<>();
    }

    public static <K> Wrapper<K> wrapper() {
        return new Wrapper<>();
    }

    public static class Lambda<K> {

        /**
         * 条件字段
         */
        private final List<QueryField> queryFields = new ArrayList<>();

        /**
         * 排序字段
         */
        private final List<OrderBy> orderBy = new ArrayList<>();

        private Lambda() {
        }

        public QueryWrapper<K> build() {
            return new QueryWrapper<>(queryFields, orderBy);
        }

        /**
         * 等于
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> eq(FieldFunction<K, ?> lambda, Object value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName).operator(Opt.EQ).value(value).build());
            return this;
        }

        /**
         * 不等于
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> ne(FieldFunction<K, ?> lambda, Object value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName).operator(Opt.NE).value(value).build());
            return this;
        }

        /**
         * 大于
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> gt(FieldFunction<K, ?> lambda, Comparable<?> value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.GT).value(value).build());
            return this;
        }

        /**
         * 大于等于
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> ge(FieldFunction<K, ?> lambda, Comparable<?> value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.GE).value(value).build());
            return this;
        }

        /**
         * 小于
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> lt(FieldFunction<K, ?> lambda, Comparable<?> value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LT).value(value).build());
            return this;
        }

        /**
         * 小于等于
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> le(FieldFunction<K, ?> lambda, Comparable<?> value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LE).value(value).build());
            return this;
        }

        /**
         * like '%xx%'
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> like(FieldFunction<K, ?> lambda, String value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LIKE).value("%" + value + "%").build());
            return this;
        }

        /**
         * like 'xx%'
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> likeRight(FieldFunction<K, ?> lambda, String value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LIKE).value(value + "%").build());
            return this;
        }


        /**
         * like '%xx'
         *
         * @param lambda 字段名
         * @param value  值
         * @return this
         */
        public Lambda<K> likeLeft(FieldFunction<K, ?> lambda, String value) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LIKE).value("%" + value).build());
            return this;
        }

        /**
         * between lower，upper
         *
         * @param lambda 字段名
         * @param min    最小值
         * @param max    最大值
         * @return
         */
        public Lambda<K> between(FieldFunction<K, ?> lambda, Object min, Object max) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.BETWEEN).value(new Object[]{min, max}).build());
            return this;
        }

        /**
         * in  集合
         *
         * @param lambda 字段名
         * @param values 值
         * @return this
         */
        public Lambda<K> in(FieldFunction<K, ?> lambda, Iterable<?> values) {
            List<Object> valuesList = new ArrayList<>();
            values.forEach(valuesList::add);
            in(lambda, valuesList.toArray());
            return this;
        }

        /**
         * in  数组/可变参
         *
         * @param lambda 字段名
         * @param values 值
         * @return this
         */
        public Lambda<K> in(FieldFunction<K, ?> lambda, Object... values) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.IN).value(values).build());
            return this;
        }

        /**
         * notIn  数组/可变参
         *
         * @param lambda 字段名
         * @param values 值
         * @return this
         */
        public Lambda<K> notIn(FieldFunction<K, ?> lambda, Object... values) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.NOT_IN).value(values).build());
            return this;
        }

        /**
         * notIn  集合
         *
         * @param lambda 字段名
         * @param values 值
         * @return this
         */
        public Lambda<K> notIn(FieldFunction<K, ?> lambda, Iterable<?> values) {
            List<Object> valuesList = new ArrayList<>();
            values.forEach(valuesList::add);
            notIn(lambda, valuesList.toArray());
            return this;
        }

        /**
         * isNull
         *
         * @param lambda 字段名
         * @return this
         */
        public Lambda<K> isNull(FieldFunction<K, ?> lambda) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.IS_NULL).build());
            return this;
        }


        /**
         * isNotNull
         *
         * @param lambda 字段名
         * @return this
         */
        public Lambda<K> isNotNull(FieldFunction<K, ?> lambda) {
            String fieldName = getFieldName(lambda);
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.NOT_NULL).build());
            return this;
        }

        /**
         * 根据字段排序 正序
         *
         * @param lambda 字段名
         * @return
         */
        public Lambda<K> orderBy(FieldFunction<K, ?> lambda, boolean desc) {
            String fieldName = getFieldName(lambda);
            orderBy.add(OrderBy.builder().isDesc(desc).property(fieldName).build());
            return this;
        }

        private <T> String getFieldName(FieldFunction<T, ?> func) {
            if (!func.getClass().isSynthetic()) {
                throw new RuntimeException("该方法仅能传入 lambda 表达式产生的合成类");
            }
            try {
                // 通过获取对象方法，判断是否存在该方法
                Method method = func.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                // 利用jdk的SerializedLambda 解析方法引用
                java.lang.invoke.SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
                String getter = serializedLambda.getImplMethodName();
                return resolveFieldName(getter);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        private String resolveFieldName(String getMethodName) {
            if (getMethodName.startsWith("get")) {
                getMethodName = getMethodName.substring(3);
            } else if (getMethodName.startsWith("is")) {
                getMethodName = getMethodName.substring(2);
            }
            // 小写第一个字母
            return firstToLowerCase(getMethodName);
        }

        private String firstToLowerCase(String param) {
            if (Utils.useString(param).isBlank()) {
                return "";
            }
            return param.substring(0, 1).toLowerCase() + param.substring(1);
        }

    }

    public static class Wrapper<K> {

        private Wrapper() {
        }

        public QueryWrapper<K> build() {
            return new QueryWrapper<>(queryFields, orderBy);
        }

        /**
         * 条件字段
         */
        private final List<QueryField> queryFields = new ArrayList<>();

        /**
         * 排序字段
         */
        private final List<OrderBy> orderBy = new ArrayList<>();

        /**
         * 等于
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> eq(String fieldName, Object value) {
            queryFields.add(QueryField.builder().fieldName(fieldName).operator(Opt.EQ).value(value).build());
            return this;
        }

        /**
         * 不等于
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> ne(String fieldName, Object value) {
            queryFields.add(QueryField.builder().fieldName(fieldName).operator(Opt.NE).value(value).build());
            return this;
        }

        /**
         * 大于
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> gt(String fieldName, Comparable<?> value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.GT).value(value).build());
            return this;
        }

        /**
         * 大于等于
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> ge(String fieldName, Comparable<?> value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.GE).value(value).build());
            return this;
        }

        /**
         * 小于
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> lt(String fieldName, Comparable<?> value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LT).value(value).build());
            return this;
        }

        /**
         * 小于等于
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> le(String fieldName, Comparable<?> value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LE).value(value).build());
            return this;
        }

        /**
         * like '%xx%'
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> like(String fieldName, String value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LIKE).value("%" + value + "%").build());
            return this;
        }

        /**
         * like 'xx%'
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> likeRight(String fieldName, String value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LIKE).value(value + "%").build());
            return this;
        }


        /**
         * like '%xx'
         *
         * @param fieldName 字段名
         * @param value     值
         * @return this
         */
        public Wrapper<K> likeLeft(String fieldName, String value) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.LIKE).value("%" + value).build());
            return this;
        }

        /**
         * between lower，upper
         *
         * @param fieldName 字段名
         * @param min       最小值
         * @param max       最大值
         * @return
         */
        public Wrapper<K> between(String fieldName, Object min, Object max) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.BETWEEN).value(new Object[]{min, max}).build());
            return this;
        }

        /**
         * in  集合
         *
         * @param fieldName 字段名
         * @param values    值
         * @return this
         */
        public Wrapper<K> in(String fieldName, Iterable<?> values) {
            List<Object> valuesList = new ArrayList<>();
            values.forEach(valuesList::add);
            in(fieldName, valuesList.toArray());
            return this;
        }

        /**
         * in  数组/可变参
         *
         * @param fieldName 字段名
         * @param values    值
         * @return this
         */
        public Wrapper<K> in(String fieldName, Object... values) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.IN).value(values).build());
            return this;
        }

        /**
         * notIn  数组/可变参
         *
         * @param fieldName 字段名
         * @param values    值
         * @return this
         */
        public Wrapper<K> notIn(String fieldName, Object... values) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.NOT_IN).value(values).build());
            return this;
        }

        /**
         * notIn  集合
         *
         * @param fieldName 字段名
         * @param values    值
         * @return this
         */
        public Wrapper<K> notIn(String fieldName, Iterable<?> values) {
            List<Object> valuesList = new ArrayList<>();
            values.forEach(valuesList::add);
            notIn(fieldName, valuesList.toArray());
            return this;
        }

        /**
         * isNull
         *
         * @param fieldName 字段名
         * @return this
         */
        public Wrapper<K> isNull(String fieldName) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.IS_NULL).build());
            return this;
        }


        /**
         * isNotNull
         *
         * @param fieldName 字段名
         * @return this
         */
        public Wrapper<K> isNotNull(String fieldName) {
            queryFields.add(QueryField.builder().fieldName(fieldName)
                    .operator(Opt.NOT_NULL).build());
            return this;
        }

        /**
         * 根据字段排序 正序
         *
         * @param fieldName 字段名
         * @return
         */
        public Wrapper<K> orderBy(String fieldName, boolean desc) {
            orderBy.add(OrderBy.builder().isDesc(desc).property(fieldName).build());
            return this;
        }

    }
}
