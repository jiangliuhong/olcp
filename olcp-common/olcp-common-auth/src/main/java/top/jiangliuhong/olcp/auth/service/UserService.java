package top.jiangliuhong.olcp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.jiangliuhong.olcp.auth.bean.SimpleUserDO;
import top.jiangliuhong.olcp.auth.dao.SimpleUserRepository;

public class UserService {

    @Autowired
    private SimpleUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SimpleUserDO save(SimpleUserDO userDO) {
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
//        userDO.setId("1");
        userDO = userRepository.save(userDO);
        return userDO;
    }

    public SimpleUserDO getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
