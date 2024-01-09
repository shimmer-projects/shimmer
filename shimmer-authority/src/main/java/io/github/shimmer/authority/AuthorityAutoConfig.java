package io.github.shimmer.authority;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;


/**
 * <p>认证授权模块自动装载类</p>
 * <p>
 * Created on 2024-01-07 16:53
 *
 * @author yu_haiyang
 */
@Configuration
@ComponentScan(basePackages = "io.github.shimmer.authority")
@Indexed
public class AuthorityAutoConfig {
}
