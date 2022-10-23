package top.jiangliuhong.olcp.common.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.handler.CommonEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(CommonEntityListener.class)
public class BaseDO {
    @Id
    private String id;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "update_user")
    private String updateUser;
}
