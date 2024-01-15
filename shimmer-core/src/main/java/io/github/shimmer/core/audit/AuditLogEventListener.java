package io.github.shimmer.core.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>系统访问审计日志，log打印监听器</p>
 * Created on 2024-01-15 15:06
 *
 * @author yu_haiyang
 */

@Component
@Slf4j
public class AuditLogEventListener {

    @EventListener
    public void onAuditLogEvent(AuditLogEvent event) {
        log.info("接口访问审计日志：{}", event.getSource().toString());
    }
}
