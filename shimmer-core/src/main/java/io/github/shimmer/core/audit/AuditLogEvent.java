package io.github.shimmer.core.audit;

import org.springframework.context.ApplicationEvent;

/**
 * <p>日志记录监听事件</p>
 * Created on 2024-01-15 15:05
 *
 * @author yu_haiyang
 */
public class AuditLogEvent extends ApplicationEvent {
    public AuditLogEvent(Object source) {
        super(source);
    }
}
