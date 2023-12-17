package io.github.shimmer.core.constant;


/**
 * <p>
 * 锁定标识：锁定与未锁定两种状态。该状态通常用来标识数据是否被锁定。在登录用户中使用的最为广泛。
 * </p>
 * Created on 2023-12-16 08:28
 *
 * @author yu_haiyang
 */
public enum Lock implements BaseEnum<Integer> {
    /**
     * 锁定
     */
    LOCKED(0, "已锁定", "一种锁定标志位，标识已经被锁定"),
    /**
     * 未锁定
     */
    UNLOCKED(1, "未锁定", "一种锁定标志位，标识没有被锁定");

    private final Integer code;
    private final String label;
    private final String description;

    Lock(Integer code, String label, String description) {
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
