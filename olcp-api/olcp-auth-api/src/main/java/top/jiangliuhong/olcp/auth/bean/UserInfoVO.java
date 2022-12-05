package top.jiangliuhong.olcp.auth.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "用户信息")
public class UserInfoVO {
    private String id;
    private String username;
    private String nickname;
    private List<String> role;
    private String avatar;
}
