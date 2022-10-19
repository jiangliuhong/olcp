package top.jiangliuhong.olcp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.jiangliuhong.olcp.auth.bean.JwtUser;
import top.jiangliuhong.olcp.auth.bean.UserDO;
import top.jiangliuhong.olcp.auth.dao.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        UserDO user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        // 返回UserDetails实现类
        return new JwtUser(user);
    }
}
