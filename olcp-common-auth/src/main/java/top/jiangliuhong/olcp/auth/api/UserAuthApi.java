package top.jiangliuhong.olcp.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jiangliuhong.olcp.auth.bean.vo.LoginVO;
import top.jiangliuhong.olcp.auth.service.UserAuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthApi {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/login")
    @Tag(name = "登录")
    public String login(LoginVO loginVO) {
        return userAuthService.login(loginVO.getUsername(), loginVO.getPassword());
    }

}
