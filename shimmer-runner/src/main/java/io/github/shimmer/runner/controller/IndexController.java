package io.github.shimmer.runner.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {


    @GetMapping("hello")
    @ResponseBody
    public String hello(String name) {
        return "Hello " + name;
    }
}
