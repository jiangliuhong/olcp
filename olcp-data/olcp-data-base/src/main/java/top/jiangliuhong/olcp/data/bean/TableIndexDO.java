package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.IndexType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_table_index")
public class TableIndexDO extends BaseDO {
    private String name;
    private String title;
    private IndexType type;
    private String tableId;
    private String fieldId;
    private String parent;
}
