package top.jiangliuhong.olcp.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import top.jiangliuhong.olcp.auth.service.JwtAuthenticationTokenFilter;
import top.jiangliuhong.olcp.auth.service.JwtTokenHandler;

@Configuration
public class SpringSecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //省略HttpSecurity的配置
        //httpSecurity.userDetailsService(userDetailsService()).authenticationManager()
        //httpSecurity.addFilter(jwtAuthenticationTokenFilter());
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        users.createUser(User.withUsername("javaboy").password("{noop}123").roles("admin").build());
        users.createUser(User.withUsername("江南一点雨").password("{noop}123").roles("admin").build());
        return users;
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/login");
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
}
