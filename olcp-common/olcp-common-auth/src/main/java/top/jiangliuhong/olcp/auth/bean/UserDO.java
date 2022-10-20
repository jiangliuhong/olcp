package top.jiangliuhong.olcp.auth.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import top.jiangliuhong.olcp.common.bean.BaseDO;

@Getter
@Setter
@Table("sys_user")
public class UserDO extends BaseDO {

    private String username;
    private String password;
    private String nickname;

}
