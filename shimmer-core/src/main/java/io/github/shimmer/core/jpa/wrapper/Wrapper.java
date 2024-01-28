package io.github.shimmer.core.jpa.wrapper;

import org.springframework.data.jpa.domain.Specification;

/**
 * <p>顶级包装器</p>
 * Created on 2024-01-20 19:25
 *
 * @author yu_haiyang
 */
interface Wrapper<T> extends Specification<T> {

    /**
     * 等于
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> eq(String fieldName, Object value);
    

    /**
     * 不等于
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> ne(String fieldName, Object value);
    
    /**
     * 大于
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> gt(String fieldName, Comparable<?> value);

    /**
     * 大于等于
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> ge(String fieldName, Comparable<?> value);

    /**
     * 小于
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> lt(String fieldName, Comparable<?> value);
    

    /**
     * 小于等于
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> le(String fieldName, Comparable<?> value);
    

    /**
     * like '%xx%'
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> like(String fieldName, String value);
     

    /**
     * like 'xx%'
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> likeRight(String fieldName, String value);
     

    /**
     * like '%xx'
     *
     * @param fieldName 字段名
     * @param value     值
     * @return this
     */
    Wrapper<T> likeLeft(String fieldName, String value);
    
    /**
     * between lower，upper
     *
     * @param fieldName 字段名
     * @param min       最小值
     * @param max       最大值
     * @return this
     */
    Wrapper<T> between(String fieldName, Object min, Object max);
     

    /**
     * in  集合
     *
     * @param fieldName 字段名
     * @param values    值
     * @return this
     */
    Wrapper<T> in(String fieldName, Iterable<?> values);
     

    /**
     * in  数组/可变参
     *
     * @param fieldName 字段名
     * @param values    值
     * @return this
     */
    Wrapper<T> in(String fieldName, Object... values);
   

    /**
     * notIn  数组/可变参
     *
     * @param fieldName 字段名
     * @param values    值
     * @return this
     */
    Wrapper<T> notIn(String fieldName, Object... values);
  

    /**
     * notIn  集合
     *
     * @param fieldName 字段名
     * @param values    值
     * @return this
     */
    Wrapper<T> notIn(String fieldName, Iterable<?> values);
 

    /**
     * isNull
     *
     * @param fieldName 字段名
     * @return this
     */
    Wrapper<T> isNull(String fieldName);


    /**
     * isNotNull
     *
     * @param fieldName 字段名
     * @return this
     */
    Wrapper<T> isNotNull(String fieldName);

    /**
     * 根据字段排序 正序
     *
     * @param fieldName 字段名
     * @return this
     */
    Wrapper<T> orderBy(String fieldName, boolean desc);
}
