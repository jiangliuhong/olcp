package top.jiangliuhong.olcp.engine.load.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.engine.type.FieldType;

@Getter
@Setter
public class FieldInfo {
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
