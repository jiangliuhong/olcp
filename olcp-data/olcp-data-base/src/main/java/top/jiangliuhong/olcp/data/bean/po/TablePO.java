package top.jiangliuhong.olcp.data.bean.po;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.type.EngineType;

import java.util.List;

@Getter
@Setter
public class TablePO {

    private String id;
    private String name;
    private String title;
    private String appId;
    private Boolean virtualTable;
    private EngineType engineType = EngineType.InnoDB;
    private String defaultCharset = "UTF-8";
    private List<TableFieldPO> fields;

}
