package io.github.shimmer.core.constant;


/**
 * <p>
 * 逻辑删除的标识：
 * 删除与否只有两种状态，删除与未删除
 * </p>
 * Created on 2023-12-16 08:28
 *
 * @author yu_haiyang
 */
public enum Delete implements BaseEnum<Integer> {
    /**
     * 锁定
     */
    DELETED(0, "已删除", "删除标志位，逻辑删除标识，标识已经被删除"),
    /**
     * 未锁定
     */
    UNDELETED(1, "未删除", "删除标志位，逻辑删除标识，标识没有被删除");

    private final Integer code;
    private final String label;
    private final String description;

    Delete(Integer code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    /**
     * 映射的编码 暂时支持 Integer 和 String 两种类型
     *
     * @return 编码
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 编码所对应的可理解的名称
     *
     * @return 名称
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * 对于该项枚举具体的描述说明信息，说明其实际意义以及使用场景
     *
     * @return 描述信息
     */
    @Override
    public String getDescription() {
        return description;
    }
}
