package io.github.shimmer.core.audit;

import io.github.shimmer.core.constant.Result;
import org.springframework.boot.actuate.endpoint.OperationType;

/**
 * <p>审计日志详情</p>
 * Created on 2024-01-15 15:11
 *
 * @author yu_haiyang
 */
public record AuditLogInfo(

        // 操作模块
        String module,

        // 操作类型
        OperationType type,

        // 请求方式
        String method,

        // 操作人员
        String user,

        // 请求url
        String url,

        //操作地址
        String ip,

        // 操作说明
        String content,

        // 执行时长
        Long cost,

        // 操作时间
        Long time,
        // 请求结果， 1是成功， 0 是失败
        Result result
) {
}
