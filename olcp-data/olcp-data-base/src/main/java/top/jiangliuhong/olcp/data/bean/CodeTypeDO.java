package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_code_type")
public class CodeTypeDO extends BaseDO {
    private String name;
    private String title;
    private Boolean active;
    private String appId;
}
