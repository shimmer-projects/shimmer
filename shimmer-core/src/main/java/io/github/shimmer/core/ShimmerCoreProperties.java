package io.github.shimmer.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>core模块中可配置的相关属性定义</p>
 * Created on 2024-01-10 16:48
 *
 * @author yu_haiyang
 */

@Data
@ConfigurationProperties(prefix = "shimmer.core")
public class ShimmerCoreProperties {


    /**
     * 响应结果包装例外处理，与NoResponseWrapper {@link io.github.shimmer.core.response.NoResponseWrapper }注解功能相同，但是作用范围更广，该配置针对包路径进行排除处理。
     */
    private List<String> noResponseWrapperPackages;

    /**
     * 在全局异常处理时，日志中是否输出异常信息
     */
    private boolean printExceptionInGlobalAdvice = true;

    /**
     * 日志审计功能配置
     */
    private AuditProperties audit = new AuditProperties();

    @Data
    public static class AuditProperties {

        /**
         * 是否启用日志审计功能
         */
        private Boolean enabled = true;

        /**
         * 是否启用全局日志，无论接口是否使用AuditLog进行标记，都会对日志进行记录
         */
        private Boolean enableGlobal = true;
    }
}
