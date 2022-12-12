package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.FieldType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_table_field")
public class TableFieldDO extends BaseDO {
    private String name;
    private String title;
    private String tableId;
    private String appId;
    @Enumerated(EnumType.STRING)
    private FieldType type;
    private Integer maxLength;
    private Integer maxPrecision;
    private String defaultValue;
    private String shortDescription;
    private Boolean required = false;
    private Boolean uniqueness = false;
    private String referenceTableId;
    private Boolean systemField = false;
    private Integer sn;
}
