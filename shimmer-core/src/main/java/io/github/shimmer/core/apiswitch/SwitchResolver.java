package io.github.shimmer.core.apiswitch;

/**
 * <p>定义解析器接口</p>
 * Created on 2024-01-10 13:25
 *
 * @author yu_haiyang
 */
public interface SwitchResolver {
    boolean resolver(String key);

    void config(String key, Integer status);
}
