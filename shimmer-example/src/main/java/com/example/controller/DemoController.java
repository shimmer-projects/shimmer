package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>示例自定义开发controller</p>
 * <p>
 * Created on 2023-12-19 19:52
 *
 * @author yu_haiyang
 */

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/current")
    public String currentTime() {
        return LocalDateTime.now().toString();
    }
}
