package io.github.shimmer.core.constant;

/**
 * <p>
 * 断言，定义是否枚举
 * </p>
 * Created on 2023-12-16 17:35
 *
 * @author yu_haiyang
 */
public enum Result implements BaseEnum<Integer> {
    FAIL(0, "失败", ""),
    SUCCESS(1, "成功", "");

    private final Integer code;
    private final String label;
    private final String description;

    Result(Integer code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
