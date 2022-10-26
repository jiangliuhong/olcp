package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.RelationshipType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_table_relationship")
public class TableRelationshipDO extends BaseDO {
    private String tableId;
    private String relatedTableId;
    private String relatedFieldId;
    private RelationshipType type;
    private Boolean readOnly;
}
