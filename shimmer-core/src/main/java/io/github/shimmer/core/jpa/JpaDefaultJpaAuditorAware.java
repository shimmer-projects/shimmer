package io.github.shimmer.core.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p>数据审计获取用户的接口</p>
 *
 * @author yu_haiyang
 */
@Slf4j
@Component
public class JpaDefaultJpaAuditorAware implements AuditorAware<Long> {
    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        // TODO 返回会话中的用户信息
        return Optional.of(1L);
    }
}
