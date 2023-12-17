package io.github.shimmer.core.constant;


/**
 * <p>
 * 人或者动物的性别标识：
 * 常规来说只有男女一说，但是在某些情况下可以允许保密的，因此提供三种内容
 * 关于动物，一般不以男女来定义，有公母，雌雄等很多种表达。但是无非都是这两种状态，不要矫枉过正。统统以男女定义。
 * </p>
 * Created on 2023-12-16 11:30
 *
 * @author yu_haiyang
 */
public enum Sex implements BaseEnum<Integer> {

    /**
     * 保密
     */
    SECRECY(0, "保密", "未知性别"),
    /**
     * 男性
     */
    MAN(1, "男", "男性性别"),
    /**
     * 女性
     */
    WOMAN(2, "女", "女性性别");

    private final Integer code;
    private final String label;
    private final String description;

    Sex(Integer code, String label, String description) {
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
