package top.jiangliuhong.olcp.data.bean.cache;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableCachePO {

    private String id;
    private String name;
    private String title;
    private String appId;
    private Boolean virtualTable;
    private List<TableFieldCachePO> fields;

}
