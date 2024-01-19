package io.github.shimmer.core.apiswitch;

import java.lang.annotation.*;

/**
 * <p>定义接口开关注解</p>
 * <br>
 * <p>
 * example:
 * <pre>
 * {@code
 *  @RestController
 *  public class ExampleController {
 *     @GetMapping("/q1")
 *     @ApiSwitch(key = "swtich$q1", fallback = "q1_fallback")
 *     public Map<String, Object> q1() {
 *         return Map.of("a", "b", "c", "d");
 *     }
 *     public String q1_fallback() {
 *         return "接口维护中";
 *     }
 *  }
 * }
 *  </pre>
 * Created on 2024-01-10 13:24
 *
 * @author yu_haiyang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ApiSwitch {

    /**
     * 接口对应的key，通过可以该key查询接口是否关闭
     */
    String key() default "";

    /**
     * 如果不提供降级方法的情况下，接口会返回FORBIDDEN的状态，desc将会作为响应体中的描述信息输出
     */
    String desc() default "";

    /**
     * 开启后降级的方法名，该方法的返回值一定要与原方法的返回类型一致，否则将会转换成字符串替换为desc的内容
     */
    String fallback() default "";

}
