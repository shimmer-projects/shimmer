package io.github.shimmer.core.audit;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * Created on 2024-01-15 15:55
 *
 * @author yu_haiyang
 */


@RequiredArgsConstructor
public enum OperationType {

    /**
     * 操作类型
     */
    ADD("添加"),
    DEL("删除"),
    UPDATE("修改"),
    QUERY("查询"),
    LOGIN("登录"),
    LOGOUT("登出"),
    DOWNLOAD("下载"),
    UPLOAD("上传"),
    BACKUP("备份"),
    RECOVER("还原");

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", name, name());
    }
}
