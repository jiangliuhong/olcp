package top.jiangliuhong.olcp.auth.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "登陆信息")
public class LoginVO {
    private String username;
    private String password;
}
