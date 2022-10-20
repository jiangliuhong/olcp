package top.jiangliuhong.olcp.auth.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.jiangliuhong.olcp.auth.bean.UserDO;
import top.jiangliuhong.olcp.auth.bean.vo.UserVO;
import top.jiangliuhong.olcp.auth.dao.UserRepository;

public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserVO save(UserDO userDO) {
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        userDO = userRepository.save(userDO);
        UserVO resUser = new UserVO();
        BeanUtils.copyProperties(userDO, resUser);
        return resUser;
    }

}
