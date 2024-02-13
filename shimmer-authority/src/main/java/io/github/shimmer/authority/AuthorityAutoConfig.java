package io.github.shimmer.authority;

import io.github.shimmer.authority.filter.JwtAuthenticationFilter;
import io.github.shimmer.authority.filter.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


/**
 * <p>认证授权模块自动装载类</p>
 * <p>
 * Created on 2024-01-07 16:53
 *
 * @author yu_haiyang
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableConfigurationProperties(AuthorityProperties.class)
@ComponentScan(basePackages = "io.github.shimmer.authority")
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "shimmer.authority", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AuthorityAutoConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用csrf，前后端分离一定要禁用
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用http basic认证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用默认登录功能
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(configurer -> configurer.configurationSource(corCsonfigurationSource()))
                .addFilterBefore(jwtAuthenticationFilter(), AuthorizationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();

//        return new InMemoryUserDetailsManager(userDetails);
        return new UserDetailsServiceImpl();
    }


    @Bean
    public CorsConfigurationSource corCsonfigurationSource() {
        // 1. 添加 CORS配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // 放行哪些原始域
        configuration.addAllowedOrigin("*");
        // 是否发送 Cookie
        configuration.setAllowCredentials(false);
        // 放行哪些请求方式
        configuration.addAllowedMethod("*");
        // 放行哪些原始请求头部信息
        configuration.addAllowedHeader("*");
        // 暴露哪些头部信息
        configuration.addExposedHeader("*");
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
