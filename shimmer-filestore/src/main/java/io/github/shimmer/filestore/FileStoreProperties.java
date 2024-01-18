package io.github.shimmer.filestore;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p></p>
 * Created on 2024-01-17 10:58
 *
 * @author yu_haiyang
 */

@Data
@ConfigurationProperties(prefix = "shimmer.filestore")
public class FileStoreProperties {

    /**
     * 是否启用文件存储模块
     */
    private boolean enabled;
}
