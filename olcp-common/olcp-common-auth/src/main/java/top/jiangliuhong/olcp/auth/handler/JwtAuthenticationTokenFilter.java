package top.jiangliuhong.olcp.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import top.jiangliuhong.olcp.auth.properties.AuthProperties;
import top.jiangliuhong.olcp.auth.properties.JwtProperties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private JwtTokenHandler jwtTokenHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        JwtProperties jwtProperties = authProperties.getJwt();
        String authHeader = request.getHeader(jwtProperties.getTokenHeader());
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(jwtProperties.getTokenHead())) {
            String authToken = authHeader.substring(jwtProperties.getTokenHead().length());
            String username = jwtTokenHandler.getUserNameFromToken(authToken);
            log.info("checking authentication " + username);
            if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // TODO 目前每次请求都查询用户信息，考虑优化
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // 校验token
                if (jwtTokenHandler.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    log.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
