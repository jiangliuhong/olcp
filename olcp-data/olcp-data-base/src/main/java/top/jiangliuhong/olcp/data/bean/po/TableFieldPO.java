package top.jiangliuhong.olcp.data.bean.po;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.type.FieldType;

@Getter
@Setter
public class TableFieldPO {

    private String id;
    private String name;
    private String title;
    private String tableId;
    private String appId;
    private FieldType type;
    private Integer maxLength;
    private Integer maxPrecision;
    private String defaultValue;
    private String shortDescription;
    private Boolean required = false;
    private Boolean uniqueness = false;
    private String referenceTableId;
}
