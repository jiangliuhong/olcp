package top.jiangliuhong.olcp.auth.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import top.jiangliuhong.olcp.auth.bean.JwtUser;
import top.jiangliuhong.olcp.auth.bean.UserDO;

/**
 * auth (user) utils
 */
public class AuthUtils {

    public static UserDO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)authentication;
        JwtUser jwtUser = (JwtUser)authenticationToken.getCredentials();
        if (jwtUser == null) {
            return null;
        }
        return jwtUser.getUser();
    }
}