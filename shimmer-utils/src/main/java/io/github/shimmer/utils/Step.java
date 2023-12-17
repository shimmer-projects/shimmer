package io.github.shimmer.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 步进器
 * <br/>
 * Created on 2023-12-17 10:34
 *
 * @author yu_haiyang
 */
public final class Step {

    private final AtomicInteger step = new AtomicInteger(0);

    private Integer stepLength = 1;

    public Step() {
    }

    public Step(int stepLength) {
        this.stepLength = stepLength;
    }

    public Step(int initialValue, int stepLength) {
        this.step.set(initialValue);
        this.stepLength = stepLength;
    }

    public Integer next() {
        return step.getAndAdd(stepLength);
    }
}
