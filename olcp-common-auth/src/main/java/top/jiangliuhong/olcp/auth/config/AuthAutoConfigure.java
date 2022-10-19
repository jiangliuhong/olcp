package top.jiangliuhong.olcp.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import top.jiangliuhong.olcp.auth.api.UserAuthApi;
import top.jiangliuhong.olcp.auth.properties.JwtProperties;
import top.jiangliuhong.olcp.auth.service.*;

@EnableConfigurationProperties({JwtProperties.class})
public class AuthAutoConfigure {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //省略HttpSecurity的配置
        //httpSecurity.userDetailsService(userDetailsService()).authenticationManager()
        //httpSecurity.addFilter(jwtAuthenticationTokenFilter());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/api/v1/auth/login");
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
    public UserAuthenticationProvider olcpAuthenticationProvider() {
        return new UserAuthenticationProvider();
    }

    @Bean
    public JwtTokenHandler jwtTokenHandler() {
        return new JwtTokenHandler();
    }

    @Bean
    public UserAuthService userAuthService() {
        return new UserAuthService();
    }

    @Bean
    public UserAuthApi userAuthApi() {
        return new UserAuthApi();
    }
}
