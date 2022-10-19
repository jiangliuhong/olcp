package top.jiangliuhong.olcp.common.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class BaseDO {
    @Id
    private String id;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updateUser;
}
