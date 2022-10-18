package top.jiangliuhong.olcp.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.jiangliuhong.olcp.auth.bean.JwtUser;
import top.jiangliuhong.olcp.common.bean.OlcpUser;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
//        List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("username", username));
//        User user;
//        // 判断用户是否存在
//        if (!CollectionUtils.isEmpty(userList)){
//            user = userList.get(0);
//        } else {
//            throw new UsernameNotFoundException("用户名不存在！");
//        }
        // 返回UserDetails实现类
        OlcpUser user = new OlcpUser();
        user.setUsername("test");
        user.setPassword("test");
        return new JwtUser(user);
    }
}
