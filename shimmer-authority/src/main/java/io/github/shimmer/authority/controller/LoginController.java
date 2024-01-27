package io.github.shimmer.authority.controller;

import io.github.shimmer.authority.request.LoginRequest;
import io.github.shimmer.core.annotation.Get;
import io.github.shimmer.core.annotation.Rest;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

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
    public void login(LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        System.out.println();
    }
}
