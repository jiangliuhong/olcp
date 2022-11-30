package top.jiangliuhong.olcp.data.component;

import top.jiangliuhong.olcp.common.utils.NameUtils;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.type.FieldType;
import top.jiangliuhong.olcp.data.utils.FieldTypeUtils;

public class TableFieldDefinition {

    private final TableFieldPO source;

    public TableFieldDefinition(TableFieldPO source) {
        this.source = source;
    }

    public TableFieldPO getSource() {
        return this.source;
    }

    public String getName() {
        return this.source.getName();
    }

    public String getDbName() {
        return NameUtils.camelToUnderline(this.source.getName());
    }

    public FieldType getType() {
        return this.source.getType();
    }

    public String getDbType() {
        return FieldTypeUtils.transformFieldType(this.source);
    }

}
