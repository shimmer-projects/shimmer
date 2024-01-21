package io.github.shimmer.core.jpa.wrapper;

import lombok.Getter;

/**
 * <p>支持的操作类型</p>
 * Created on 2024-01-21 9:27
 *
 * @author yu_haiyang
 */

@Getter
public enum Opt {
    EQ,
    NE,
    GE, GT, LE, LT, IN, NOT_IN, LIKE, NOT_LIKE, BETWEEN, IS_NULL, NOT_NULL;


//    private final Condition condition;

//    Opt(Condition condition) {
//        this.condition = condition;
//    }
}
