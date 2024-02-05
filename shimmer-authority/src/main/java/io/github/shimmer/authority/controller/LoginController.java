package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.LoginRequest;
import io.github.shimmer.core.annotation.Get;
import io.github.shimmer.core.annotation.Rest;
import jakarta.annotation.Resource;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

/**
 * <p>登录登出等接口</p>
 * Created on 2024-01-26 14:24
 *
 * @author yu_haiyang
 */
@Rest(name = "登录认证接口")
public class LoginController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Get(path = "/login")
    public String login(LoginRequest loginRequest) throws JoseException {
        // 进入认证
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password())
        );
        // 认证通过返回authentication 对象， 否则会抛出认证失败的异常
        // 获取用户的详情信息
        User user = (User) authentication.getPrincipal();

        JwtClaims claims = new JwtClaims();
        // 设置 Token 的签发者
        claims.setIssuer("SHIMMER-AUTH");
        // 设置过期时间
        // claims.setExpirationTime();
        // 设置过期时间为 10 分钟后
        claims.setExpirationTimeMinutesInTheFuture(10);
        claims.setSubject("AUTHORITY");
        // 设置 Token 将被发送给哪些对象
        claims.setAudience(user.getUsername());
        // claims.setNotBefore();
        // 设置生效时间为 2 分钟前
        claims.setNotBeforeMinutesInThePast(2);
        // claims.setIssuedAt();
        // 设置 Token 发布/创建 时间为当前时间
        claims.setIssuedAtToNow();
        // claims.setJwtId();
        // 为 JWT 设置一个自动生成的唯一 ID
        claims.setGeneratedJwtId();
        // 额外添加的生命属性
//        claims.setClaim("email", "email@example.com");


        // 注意密钥长短（最少32个字符）
        HmacKey hmacKey = new HmacKey("eyJraWQiOm51bGwsImFsZyI6IkhTMjU2In0".getBytes());

        JsonWebSignature jsonWebSignature = new JsonWebSignature();
        // 为 JsonWebSignature 对象添加负载：JwtClaims 对象的 Json 内容
        jsonWebSignature.setPayload(claims.toJson());
        // JWT 使用 RSA 私钥签名
        jsonWebSignature.setKey(hmacKey);
        // 可选操作
//        if (null != key) {
//            jsonWebSignature.setKeyIdHeaderValue(kid);
//        }
        // 在 JWT / JWS 上设置签名算法
        jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        String jwt = jsonWebSignature.getCompactSerialization();

        return jwt;
    }
}
