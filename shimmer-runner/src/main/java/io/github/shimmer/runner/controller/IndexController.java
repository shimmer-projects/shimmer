package io.github.shimmer.runner.controller;


import io.github.shimmer.core.debounce.LimitAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("hello")
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("void")
    public void str() {
        throw new LimitAccessException("轻点，太快了！");
    }

}
