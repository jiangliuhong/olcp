package top.jiangliuhong.olcp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.jiangliuhong.olcp.auth.bean.UserDO;
import top.jiangliuhong.olcp.auth.dao.UserRepository;

public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDO save(UserDO userDO) {
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
//        userDO.setId("1");
        userDO = userRepository.save(userDO);
        return userDO;
    }

    public UserDO getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
