package io.github.shimmer.preferences.controller;

import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/logger")
public class LogLevelController {

    /**
     * @param name  需要修改的包名，如果是修改全部日志级别，传ROOT
     * @param level 日志级别 TRACE、DEBUG、INFO、WARN、ERROR、FATAL、OFF
     */
    @GetMapping("/change")
    public Object change(String name, String level) {
        LoggingSystem system = LoggingSystem.get(LoggingSystem.class.getClassLoader());
        system.setLogLevel(name, LogLevel.valueOf(level.toUpperCase(Locale.ENGLISH)));
        return String.format("【%s】级别调整为：%s", name, level);
    }
}
