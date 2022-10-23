package top.jiangliuhong.olcp.data.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.FieldType;

@Getter
@Setter
@Entity
@Table(name = "sys_field")
public class FieldDO extends BaseDO {
    private String name;
    private String title;
    private String tableId;
    private FieldType type;
    private Integer maxLength;
    private Integer maxPrecision;
    private String defaultValue;
    private String shortDescription;
    private boolean required;
    private String referenceTableId;
}
