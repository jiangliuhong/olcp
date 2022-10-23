package top.jiangliuhong.olcp.data.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

@Getter
@Setter
@Entity
@Table(name = "sys_code_value")
public class CodeValueDO extends BaseDO {
    private String name;
    private String title;
    private Boolean active;
}
