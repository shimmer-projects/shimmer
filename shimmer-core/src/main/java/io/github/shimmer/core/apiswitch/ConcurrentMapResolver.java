package io.github.shimmer.core.apiswitch;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>基于内存是默认实现</p>
 * Created on 2024-01-10 13:26
 *
 * @author yu_haiyang
 */
@Component
public class ConcurrentMapResolver implements SwitchResolver {

    private final Set<String> denies = new HashSet<>();

    @Override
    public boolean resolver(String key) {
        return denies.contains(key);
    }

    public void config(String key, Integer status) {
        if (status != null && status == 1) {
            denies.add(key);
        } else {
            denies.remove(key);
        }
    }
}
