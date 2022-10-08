package top.jiangliuhong.olcp.engine.bean;

import lombok.Getter;
import top.jiangliuhong.olcp.engine.load.bean.EntityInfo;
import top.jiangliuhong.olcp.engine.load.bean.FieldInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TableInfo {

    private final String tableName;
    private final String engine = "InnoDB";
    private final String defaultCharset = "UTF-8";
    private final List<ColumnInfo> columns = new ArrayList<>();
    private final Map<String, ColumnInfo> columnMaps = new HashMap<>();
    private final ColumnInfo primary;

    public TableInfo(EntityInfo entityInfo) {
        this.tableName = entityInfo.getName();
        ColumnInfo primary = null;
        for (FieldInfo fieldInfo : entityInfo.getFieldInfos()) {
            ColumnInfo column = new ColumnInfo(fieldInfo);
            if (column.equals(entityInfo.getPrimary())) {
                primary = column;
            }
            this.columns.add(column);
        }
        this.primary = primary;

    }
}
