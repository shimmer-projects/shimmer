package io.github.shimmer.core.property;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * <p>自定义实现json配置加载</p>
 *
 * @author yu_haiyang
 */
public class JsonPropertySourceLoader implements PropertySourceLoader {
    @Override
    public String[] getFileExtensions() {
        return new String[]{"json", "json5"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        try (final InputStream inputStream = resource.getInputStream()) {
            // application.json 的内容读取到字符串 jsonString 中
            final String jsonString = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            // 将 jsonString 字符串转为  Map
            // JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
            final Map<String, Object> map = JsonFlattener.flattenAsMap(jsonString);
            // 从 Map 构建一个 PropertySource
            final MapPropertySource propertySource = new MapPropertySource(name, map);
            return List.of(propertySource);
        }
    }
}
