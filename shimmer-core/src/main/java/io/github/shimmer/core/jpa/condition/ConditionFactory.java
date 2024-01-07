package io.github.shimmer.core.jpa.condition;


import io.github.shimmer.core.jpa.condition.impl.*;

import java.lang.annotation.Annotation;

/**
 * <p>
 * 获取条件注解对应的具体实现
 * </p>
 *
 * @author yu_haiyang
 */
public class ConditionFactory {

    /**
     * 接收多个注解，但是只返回第一个匹配的注解
     *
     * @param annotations 属性上标注的注解集合
     * @return 第一个匹配的注解实现类
     */
    public static Condition getCondition(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Equal) {
                return new EqualImpl();
            } else if (annotation instanceof Great) {
                return new GreatImpl();
            } else if (annotation instanceof GreatEqual) {
                return new GreatEqualImpl();
            } else if (annotation instanceof In) {
                return new InImpl();
            } else if (annotation instanceof Less) {
                return new LessImpl();
            } else if (annotation instanceof LessEqual) {
                return new LessEqualImpl();
            } else if (annotation instanceof Like) {
                return new LikeImpl();
            } else if (annotation instanceof OrderBy) {
                return new OrderByImpl();
            }
        }
        return null;
    }
}
