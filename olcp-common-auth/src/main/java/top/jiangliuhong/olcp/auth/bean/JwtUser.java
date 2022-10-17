package top.jiangliuhong.olcp.auth.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.jiangliuhong.olcp.common.bean.OlcpUser;

import java.util.Collection;

/**
 * jwt token user
 */
public class JwtUser implements UserDetails {

    private final OlcpUser olcpUser;

    public JwtUser(OlcpUser olcpUser) {
        this.olcpUser = olcpUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.olcpUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.olcpUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO 判断账户是否过期逻辑
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO 判断账户是否锁定逻辑
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO 判断密码是否过期逻辑
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO 判断账户是否激活逻辑
        return false;
    }
}
