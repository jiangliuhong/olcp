package top.jiangliuhong.olcp.data.bean.cache;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.type.FieldType;

@Getter
@Setter
public class TableFieldCachePO {

    private String name;
    private String title;
    private String tableId;
    private String appId;
    private FieldType type;
    private String referenceTableId;
}
