package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_list_field")
public class ListFieldDO extends BaseDO {
    private String title;
    private String listId;
    private String fieldId;
    private String appId;
}
