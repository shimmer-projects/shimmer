package com.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>自定义模块bean扫描配置</p>
 * Created on 2023-12-19 19:58
 *
 * @author yu_haiyang
 */

@Configuration
@ComponentScan(basePackages = "com.example")
public class DemoConfig {
}
