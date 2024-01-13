package io.github.shimmer.preferences.controller;

import io.github.shimmer.core.apiswitch.ApiSwitch;
import io.github.shimmer.core.apiswitch.SwitchResolver;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>动态切换接口可用性控制器</p>
 * Created on 2024-01-12 23:47
 *
 * @author yu_haiyang
 */
@RestController
@RequestMapping("api-switch")
public class ApiSwitchController {

    @Resource
    private SwitchResolver resolver;

    @GetMapping("/onoff/{state}")
    public Object onoff(String key, @PathVariable("state") Integer state) {
        resolver.config(key, state);
        return "success";
    }

    @GetMapping("/q1")
    @ApiSwitch(key = "swtich$q1", resolver = "redisResolver")
    public Map<String, Object> q1() {
        return Map.of("a", "b", "c", "d");
    }

    public String q1_fallback() {
        return "接口维护中";
    }
}
