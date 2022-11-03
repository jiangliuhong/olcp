package top.jiangliuhong.olcp.data.bean.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TablePO {

    private String id;
    private String name;
    private String title;
    private String appId;
    private Boolean virtualTable;
    private List<TableFieldPO> fields;

}
