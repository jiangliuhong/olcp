package top.jiangliuhong.olcp.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jiangliuhong.olcp.auth.bean.LoginVO;
import top.jiangliuhong.olcp.auth.bean.SimpleUserDO;
import top.jiangliuhong.olcp.auth.bean.UserInfoVO;
import top.jiangliuhong.olcp.auth.bean.UserVO;
import top.jiangliuhong.olcp.auth.service.UserAuthService;
import top.jiangliuhong.olcp.auth.service.UserService;
import top.jiangliuhong.olcp.auth.utils.AuthUtils;
import top.jiangliuhong.olcp.common.utils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserAuthApi {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    /**
     * user login
     *
     * @param loginVO login vo
     * @return token string
     */
    @PostMapping("/login")
    @Tag(name = "登录")
    public Map<String, String> login(@RequestBody LoginVO loginVO) {
        if (StringUtils.isBlank(loginVO.getUsername()) || StringUtils.isBlank(loginVO.getPassword())) {
            throw new RuntimeException("请输入用户名密码");
        }
        String token = userAuthService.login(loginVO.getUsername(), loginVO.getPassword());
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return result;
    }

    @GetMapping("/current")
    public UserInfoVO getCurrentUser() {
        SimpleUserDO currentUser = AuthUtils.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        UserInfoVO user = new UserInfoVO();
        user.setId(currentUser.getId());
        user.setNickname(currentUser.getNickname());
        user.setUsername(currentUser.getUsername());
        return user;
    }


    @PostMapping("/user")
    @Tag(name = "新增修改用户")
    public UserVO addUser(@RequestBody UserVO user) {
        SimpleUserDO userDO = new SimpleUserDO();
        userDO.setUsername(user.getUsername());
        userDO.setPassword(user.getPassword());
        userDO.setNickname(user.getNickname());
        userDO.setId(user.getId());
        userDO = userService.save(userDO);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

}
