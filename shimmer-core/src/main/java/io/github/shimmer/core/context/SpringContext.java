package io.github.shimmer.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * <p>SpringContext 和 环境变量 静态注入，可以在spring容器之外获取容器中的bean和配置信息</p>
 * Created on 2023-12-17 20:25
 *
 * @author yu_haiyang
 */
@Component
@Lazy(value = false) // 必须加该注解，否则不会初始化
public class SpringContext implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext applicationContext;
    private static Environment environment;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        SpringContext.environment = environment;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }
}
