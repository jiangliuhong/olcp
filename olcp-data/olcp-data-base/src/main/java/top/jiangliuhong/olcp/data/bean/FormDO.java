package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_form")
public class FormDO extends BaseDO {
    private String name;
    private String title;
    /**
     * system,custom
     */
    private String type;
    private String appId;
    private String tableId;
}
