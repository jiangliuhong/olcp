package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.FieldType;

@Getter
@Setter
@Table("sys_table_field")
public class FieldDO extends BaseDO {
    private String name;
    private String title;
    private String table;
    private FieldType type;
    private Integer length;
    private Integer precision;
    private String defaultValue;
    private String shortDescription;
    private boolean required;
    private String reference;
}
