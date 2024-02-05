package io.github.shimmer.authority.filter;

import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * <p>系统默认的认证实现类</p>
 * Created on 2024-02-05 15:30
 *
 * @author yu_haiyang
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            return new User(username, passwordEncoder.encode("password"), new ArrayList<>());
        }
        return null;
    }
}
