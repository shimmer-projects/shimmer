package io.github.shimmer.core.constant;

import java.util.Objects;

/**
 * 规范化枚举类型的属性，通常枚举类型用来定义字典数据，字典数据的属性描述，基本上包含以下三种定义
 * 1. code 编码
 * 2. label 称号
 * 3. description 说明描述
 * <p>
 * 此接口定义通用枚举类型的get方法，以及基于code或label获取枚举类型的静态方法
 * </p>
 * Created on 2023-12-15 14:02
 *
 * @author yu_haiyang
 */
public interface BaseEnum<T> {

    /**
     * 映射的编码 暂时支持 Integer 和 String 两种类型
     *
     * @return 编码
     */
    T getCode();

    /**
     * 编码所对应的可理解的名称
     *
     * @return 名称
     */
    String getLabel();

    /**
     * 对于该项枚举具体的 描述说明信息，说明其实际意义以及使用场景
     *
     * @return 描述信息
     */
    String getDescription();

    /**
     * 按枚举的 code 获取枚举实例
     *
     * @param enumType 枚举类的类对象
     * @param code     需要获取枚举对象的code码
     * @param <R>      枚举对象的实际类型
     * @return code码对应的枚举对象
     */
    static <R extends BaseEnum<V>, V> R fromCode(Class<R> enumType, V code) {
        for (R object : enumType.getEnumConstants()) {
            if (Objects.equals(code, object.getCode())) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum code " + code + " of " + enumType.getCanonicalName());
    }

    /**
     * 按枚举的 label 获取枚举实例
     *
     * @param enumType 枚举类的类对象
     * @param label    要获取枚举对象的Label
     * @param <R>      枚举对象的实际类型
     * @return label对应的枚举对象
     */
    static <R extends BaseEnum<?>> R fromLabel(Class<R> enumType, String label) {
        for (R constant : enumType.getEnumConstants()) {
            if (Objects.equals(label, constant.getLabel())) {
                return constant;
            }
        }
        throw new IllegalArgumentException("No enum label " + label + " of " + enumType.getCanonicalName());
    }

}
