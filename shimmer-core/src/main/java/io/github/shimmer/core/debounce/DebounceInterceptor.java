package io.github.shimmer.core.debounce;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 接口防抖拦截器
 * </p>
 * Created on 2022-12-18 19:40
 *
 * @author yu_haiyang
 */
@Component
@Slf4j
public class DebounceInterceptor implements HandlerInterceptor {

    private final Map<String, Map<String, Long>> statistic = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 1. 判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod h) {

            // 2. 获取方法中的注解,看是否有该注解
            Debounce debounce = h.getMethodAnnotation(Debounce.class);
            // 2.1 : 不包含此注解,则不进行操作
            if (debounce != null) {
                // 2.2 ： 判断请求是否受限制
                if (isLimit(request, debounce)) {
                    throw new LimitAccessException("请求过快");
                }
            }
        }
        return true;
    }

    /**
     * 判断请求是否受限
     */
    public boolean isLimit(HttpServletRequest request, Debounce accessLimit) {
        // 是否拦截
        boolean debounce = false;

        // 请求限制时长 秒
        int maxSeconds = accessLimit.seconds();
        // 请求限制时长内的最大限制次数
        int maxCount = accessLimit.maxCount();

        // 当前访问时间 转10位时间戳 秒
        Long currentTime = System.currentTimeMillis() / 1000;

        // TODO 受限的缓存key ,因为这里用浏览器做测试，这里用 session id 来做唯一key, 如果是app ,可以使用 用户ID 之类的唯一标识。
        String cacheKey = request.getRemoteAddr() + ":" + request.getSession().getId() + ":" + request.getServletPath();

        // 从缓存中获取，当前这个请求访问了几次
        Map<String, Long> debounceCache = statistic.get(cacheKey);

        if (debounceCache == null) {
            debounceCache = new HashMap<>(16);
            // 初始 次数
            debounceCache.put("accessTime", currentTime);
            debounceCache.put("count", 0L);
        }

        Long accessCount = debounceCache.get("count");
        Long accessTime = debounceCache.get("accessTime");

        // 本次请求距离窗口开启时间相差秒数
        long gapTime = currentTime - accessTime;

        // 1、判断时间窗口是否过期，如果时间窗口过期重新开始，如果窗口未过期，进行计数判断
        if (gapTime >= maxSeconds) {
            // 创建新窗口
            accessTime = currentTime;
            accessCount = 1L;
        } else {
            // 2、增加访问次数
            accessCount += 1;
            // 对访问次数进行判断， 如果访问次数超过最大次数 阻断，否则，访问次数加1.
            if (accessCount > maxCount) {
                // 访问次数超过最大次数 阻断
                debounce = true;
            }
        }

        // 修改缓存
        debounceCache.put("accessTime", accessTime);
        debounceCache.put("count", accessCount);
        statistic.put(cacheKey, debounceCache);

        return debounce;
    }
}
