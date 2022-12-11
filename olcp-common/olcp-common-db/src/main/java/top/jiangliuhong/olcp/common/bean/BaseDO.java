package top.jiangliuhong.olcp.common.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import top.jiangliuhong.olcp.common.annotation.OlcpField;
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
    @OlcpField(title = "ID", type = "SysId")
    private String id;

    @Column(name = "create_time")
    @OlcpField(title = "创建时间", type = "Timestamp")
    private Date createTime;

    @Comment("修改时间")
    @Column(name = "update_time")
    @OlcpField(title = "修改时间", type = "Timestamp")
    private Date updateTime;

    @Column(name = "create_user")
    @OlcpField(title = "创建人", type = "Reference.system.sys_user")
    private String createUser;

    @Column(name = "update_user")
    @OlcpField(title = "修改人", type = "Reference.system.sys_user")
    private String updateUser;
}
