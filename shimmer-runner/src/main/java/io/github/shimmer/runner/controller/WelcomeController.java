package io.github.shimmer.runner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 欢迎页
 * <br/>
 * Created on 2023-12-17 17:23
 *
 * @author yu_haiyang
 */

@Controller
@Tag(name = "欢迎页控制器")
@Slf4j()
public class WelcomeController {

    @GetMapping("/")
    @Operation(summary = "欢迎页")
    public String index() {
        return "index";
    }

}
