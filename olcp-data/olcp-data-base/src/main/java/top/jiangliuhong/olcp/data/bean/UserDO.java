package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class UserDO extends BaseDO {
    private String username;
    private String password;
    private String nickname;
    private String appid;
    private Boolean active;
    private String email;
    private String phone;
}
