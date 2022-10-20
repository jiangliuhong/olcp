package top.jiangliuhong.olcp.auth.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "用户信息")
public class UserVO {
    @Schema(title = "用户id")
    private String id;
    @Schema(title = "用户名")
    private String username;
    @Schema(title = "用户密码")
    private String password;
    @Schema(title = "用户昵称")
    private String nickname;
}
