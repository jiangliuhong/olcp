package top.jiangliuhong.olcp.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jiangliuhong.olcp.auth.bean.UserDO;
import top.jiangliuhong.olcp.auth.bean.vo.LoginVO;
import top.jiangliuhong.olcp.auth.bean.vo.UserVO;
import top.jiangliuhong.olcp.auth.service.UserAuthService;
import top.jiangliuhong.olcp.auth.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthApi {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Tag(name = "登录")
    public String login(@RequestBody LoginVO loginVO) {
        if (StringUtils.isBlank(loginVO.getUsername()) || StringUtils.isBlank(loginVO.getPassword())) {
            throw new RuntimeException("请输入用户名密码");
        }
        return userAuthService.login(loginVO.getUsername(), loginVO.getPassword());
    }

    @PostMapping("/user")
    @Tag(name = "新增用户")
    public UserVO addUser(UserVO user) {
        UserDO userDO = new UserDO();
        userDO.setUsername(user.getUsername());
        userDO.setPassword(user.getPassword());
        userDO.setNickname(user.getNickname());
        return userService.save(userDO);
    }

}