package top.jiangliuhong.olcp.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.FieldType;

@Getter
@Setter
@Schema(name = "sys_column")
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
