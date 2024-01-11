package io.github.shimmer.core.response.data;

import lombok.Getter;

/**
 * <p>通用错误码</p>
 * Created on 2024-01-11 13:32
 *
 * @author yu_haiyang
 */
@Getter
public enum ApiCode {

    OK("200", "成功"), ERROR("500", "系统内容异常"), INVALID("800", "参数校验不通过");
    private final String code;
    private final String msg;

    ApiCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
