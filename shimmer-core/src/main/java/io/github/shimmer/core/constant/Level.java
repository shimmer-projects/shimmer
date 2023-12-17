package io.github.shimmer.core.constant;


/**
 * <p>
 * 一些业务中常有关于等级性的定义，该类主要对一些常用等级的定义。
 * </p>
 * Created on 2023-12-16 14:21
 *
 * @author yu_haiyang
 */
public enum Level implements BaseEnum<Integer> {
    /**
     *
     */
    LOW(1, "低", ""),
    MIDDLE(2, "中", ""),
    HIGH(3, "高", "");
    private final Integer code;
    private final String label;
    private final String description;

    Level(Integer code, String label, String description) {
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
