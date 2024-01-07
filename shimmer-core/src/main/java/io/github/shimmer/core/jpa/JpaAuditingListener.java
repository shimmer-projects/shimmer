package io.github.shimmer.core.jpa;


import io.github.shimmer.core.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * <p>为了能够增加更多的审计字段，因此自定义此监听</p>
 *
 * @author yu_haiyang
 */

@Configurable
@Slf4j
public class JpaAuditingListener {

    private final String LOG_PREFIX = "实体持久化监听>>>>>> ";
    private final String LOG_POSTFIX = "<<<<<<";


    @PrePersist
    public void prePersist(BaseEntity<?> target) {
        // TODO 采取AuditorAware获取登录用户的ID，然后通过用户ID获取登录用户，再给数据添加用户名称的审计字段
        log.info("{} [{}] {}", this.LOG_PREFIX, "新增操作之前", LOG_POSTFIX);
    }


    @PostPersist
    public void postPersist(BaseEntity<?> target) {
        log.info("{} [{}] {}", this.LOG_PREFIX, "新增操作之后", LOG_POSTFIX);
    }


    @PreUpdate
    public void preUpdate(BaseEntity<?> target) {
        log.info("{} [{}] {}", this.LOG_PREFIX, "更新操作之前", LOG_POSTFIX);
    }


    @PostUpdate
    public void postUpdate(BaseEntity<?> target) {
        log.info("{} [{}] {}", this.LOG_PREFIX, "更新操作之后", LOG_POSTFIX);
    }

    @PreRemove
    public void preRemove(BaseEntity<?> target) {
        log.info("{} [{}] {}", this.LOG_PREFIX, "删除操作之前", LOG_POSTFIX);
    }

    @PostRemove
    public void PostRemove(BaseEntity<?> target) {
        log.info("{} [{}] {}", this.LOG_PREFIX, "删除操作之后", LOG_POSTFIX);
    }
}
