package io.github.shimmer.core.jackson.mask;


import io.github.shimmer.utils.Utils;


/**
 * 脱敏逻辑的统一实现
 */
public class MaskUtils {


    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号
     * 例子：李**
     */
    public static String chineseName(final String fullName) {
        if (Utils.useString(fullName).isBlank()) {
            return "";
        }
        String first = fullName.substring(0, 1);
        return Utils.useString(first).rightJust(fullName.length() - 1, '*').finish();
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号
     * 例子：李**
     */
    public static String chineseName(final String familyName, final String givenName) {
        if (Utils.useString(familyName).isBlank() || Utils.useString(givenName).isBlank()) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。
     * 例子：420**********5762
     */
    public static String idCardNum(final String id) {
        if (Utils.useString(id).isBlank()) {
            return "";
        }

        return Utils.useString(id).replace(3, id.length() - 4, '*').finish();
    }

    /**
     * [固定电话] 后四位，其他隐藏
     * 例子：****1234
     */
    public static String fixedPhone(final String num) {
        if (Utils.useString(num).isBlank()) {
            return "";
        }
        return Utils.useString(num).replace(0, num.length() - 4, '*').finish();
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏
     * 例子:138******1234
     */
    public static String mobilePhone(final String num) {
        if (Utils.useString(num).isBlank()) {
            return "";
        }
        return Utils.useString(num).replace(3, num.length() - 4, '*').finish();


    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护
     * 例子：北京市海淀区****
     *
     * @param sensitiveSize 敏感信息长度
     */
    public static String address(final String address, final int sensitiveSize) {
        if (Utils.useString(address).isBlank()) {
            return "";
        }
        final int length = Utils.useString(address).length();
        return Utils.useString(address).replace(length - sensitiveSize, length, '*').finish();
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示
     * 例子:g**@163.com
     */
    public static String email(final String email) {
        if (Utils.useString(email).isBlank()) {
            return "";
        }
        final int index = Utils.useString(email).index("@");
        if (index <= 1) {
            return email;
        } else {
            return Utils.useString(email).replace(1, index, '*').finish();
        }
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号
     * 例子:6222600**********1234
     */
    public static String bankCard(final String cardNum) {
        if (Utils.useString(cardNum).isBlank()) {
            return "";
        }
        return Utils.useString(cardNum).replace(6, cardNum.length() - 4, '*').finish();
    }

    /**
     * [api秘钥] 前3位，后3位，其他用星号隐藏每位1个星号
     * 例子:Aj3**********8Kl
     */
    public static String apiSecret(final String cardNum) {
        if (Utils.useString(cardNum).isBlank()) {
            return "";
        }
        return Utils.useString(cardNum).replace(3, cardNum.length() - 3, '*').finish();
    }


}
