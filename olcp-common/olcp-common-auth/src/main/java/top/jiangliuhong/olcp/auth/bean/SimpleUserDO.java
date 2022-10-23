package top.jiangliuhong.olcp.auth.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class SimpleUserDO extends BaseDO {

    private String username;
    private String password;
    private String nickname;
    private String appId;

}
