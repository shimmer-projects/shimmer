package io.github.shimmer.authority.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * <p>自定义filter</p>
 * Created on 2024-02-05 11:29
 *
 * @author yu_haiyang
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    private final HmacKey hmacKey = new HmacKey("eyJraWQiOm51bGwsImFsZyI6IkhTMjU2In0".getBytes());

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("custom filter");
        final String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());

        JwtConsumer consumer = new JwtConsumerBuilder()
                // 在验证时间时留出一些余量以解决时钟偏差问题
                .setAllowedClockSkewInSeconds(30)
                // 设置解密密钥
                // .setDecryptionKey()
                // 设置解密密钥解析器
                // .setDecryptionKeyResolver()
                // .setDisableRequireSignature()
                // 必须设置过期时间
                .setRequireExpirationTime()
                // 必须设置 Subject
                .setRequireSubject()
                // 必须设置 Token 签发者
                .setExpectedIssuer("SHIMMER-AUTH")
                // 必须设置 Token 签发给谁
                .setExpectedAudience("admin")
                // 设置用于验证签名的公钥
                .setVerificationKey(hmacKey)
                // 设置允许的预期签名算法
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.HMAC_SHA256)
                .build();
        try {
            // 校验 JWT 并将其处理成 JwtClaims 对象
            JwtClaims jwtClaims = consumer.processToClaims(token);
            System.out.println("JWT validation succeeded! JwtClaims: " + jwtClaims);
            List<String> audience = jwtClaims.getAudience();
            String username = audience.get(0);
            if (username != null
                // && SecurityContextHolder.getContext().getAuthentication() == null
            ) { // 表示用户还没有连通
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

//                 if (jwtService.isToKenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
//                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
