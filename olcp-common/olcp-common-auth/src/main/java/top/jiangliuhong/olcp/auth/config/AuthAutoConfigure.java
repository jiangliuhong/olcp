package top.jiangliuhong.olcp.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.jiangliuhong.olcp.auth.handler.AuthBeforeConvertCallback;
import top.jiangliuhong.olcp.auth.handler.JwtAuthenticationTokenFilter;
import top.jiangliuhong.olcp.auth.handler.JwtTokenHandler;
import top.jiangliuhong.olcp.auth.handler.RestAuthorizationEntryPoint;
import top.jiangliuhong.olcp.auth.handler.RestfulAccessDeniedHandler;
import top.jiangliuhong.olcp.auth.properties.AuthProperties;
import top.jiangliuhong.olcp.auth.service.UserAuthService;
import top.jiangliuhong.olcp.auth.service.UserDetailsServiceImpl;
import top.jiangliuhong.olcp.auth.service.UserService;

@Configuration
@EnableConfigurationProperties({AuthProperties.class})
public class AuthAutoConfigure {

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthorizationEntryPoint restAuthorizationEntryPoint() {
        return new RestAuthorizationEntryPoint();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public JwtTokenHandler jwtTokenHandler() {
        return new JwtTokenHandler();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public UserAuthService userAuthService() {
        return new UserAuthService();
    }

    @Bean
    public AuthBeforeConvertCallback authBeforeConvertCallback() {
        return new AuthBeforeConvertCallback();
    }
}
