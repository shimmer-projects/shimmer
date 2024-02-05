package io.github.shimmer.runner.controller;


import io.github.shimmer.core.annotation.Download;
import io.github.shimmer.core.annotation.Get;
import io.github.shimmer.core.audit.AuditLog;
import io.github.shimmer.core.audit.OperationType;
import io.github.shimmer.core.debounce.LimitAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@RestController
public class IndexController {

    @Value("${shimmer.version}")
    private String version;
    @Get(path = "hello")
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

    @Get(path = "void")
    public void str() {
        throw new LimitAccessException("轻点，太快了！");
    }

    @GetMapping("/current")
    @AuditLog(content = "访问了current接口", type = OperationType.QUERY)
    public String currentTime() {
        return LocalDateTime.now().toString();
    }

    //    @Download(path = "down", source = "classpath:application.yml", filename = "Java魔法类：Unsafe应用解析.md")
    @Download(path = "down", source = "https://psstatic.cdn.bcebos.com/video/wiseindex/aa6eef91f8b5b1a33b454c401_1660835115000.png")
    public void down() throws FileNotFoundException {
//        return new File("D:\\技术\\personal\\AbstractQueuedSynchronizer.java");
//        return new FileInputStream("D:\\技术\\personal\\Java魔法类：Unsafe应用解析.md");
    }

    @Get(path = "version")
    public String version() {
        return version;
    }
}
