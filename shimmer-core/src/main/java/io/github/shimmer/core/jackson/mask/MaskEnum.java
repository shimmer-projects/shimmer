package io.github.shimmer.core.jackson.mask;

/**
 * 内置实现的脱敏类型
 */
public enum MaskEnum {
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,

    /**
     * 秘钥
     */
    API_SECRET;
}
