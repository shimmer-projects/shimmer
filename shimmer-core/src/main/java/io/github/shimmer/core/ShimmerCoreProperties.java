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
}
