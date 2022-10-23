package top.jiangliuhong.olcp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.jiangliuhong.olcp.auth.handler.JwtTokenHandler;
import top.jiangliuhong.olcp.common.exception.BusinessException;

public class UserAuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    public String login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // String pwd = passwordEncoder.encode("test");
        // test: $2a$10$BUuG0a5K7cVd6ktES5.oEOVtGQFnIg3csRLL28bBzTaGWl4rGimyO
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BusinessException("用户名与密码不匹配");
        }
        // 更新 security 登录对象
        UsernamePasswordAuthenticationToken authentication
            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenHandler.generateToken(userDetails);
    }
}
