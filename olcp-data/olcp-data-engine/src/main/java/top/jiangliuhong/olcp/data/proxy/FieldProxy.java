package top.jiangliuhong.olcp.data.proxy;

import top.jiangliuhong.olcp.common.utils.NameUtils;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.utils.FieldTypeUtils;

public class FieldProxy {

    private final TableFieldPO source;

    public FieldProxy(TableFieldPO source) {
        this.source = source;
    }

    public TableFieldPO getSource() {
        return this.source;
    }

    public String getName() {
        return NameUtils.camelToUnderline(this.source.getName());
    }

    public String getType() {
        return FieldTypeUtils.transformFieldType(this.source);
    }

}
