package io.github.shimmer.dataway.controller;

import io.github.shimmer.core.annotation.Rest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>核心控制器</p>
 * Created on 2024-02-01 11:23
 *
 * @author yu_haiyang
 */

@Rest(path = "${shimmer.dataway.base-path}", name = "")
public class DatawayController {

    /**
     * get请求处理器
     *
     * @param params url中携带的参数
     */
    @GetMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getExec(@RequestParam Map<String, Object> params) {
        System.out.println("get");
        return null;
    }

    @PostMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object postExec(@RequestParam Map<String, Object> param, @RequestBody Map<String, Object> params) {
        System.out.println("post");
        return null;
    }
}
