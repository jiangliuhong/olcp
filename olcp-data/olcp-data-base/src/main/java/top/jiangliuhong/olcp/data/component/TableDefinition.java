package top.jiangliuhong.olcp.data.component;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.utils.NameUtils;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.exception.TableException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * table proxy ,add extend function
 */
public class TableDefinition {

    private final TablePO source;
    private final AppPO app;

    public TableDefinition(TablePO table) {
        this.source = table;
        this.app = CacheUtils.getCacheValue(CacheNames.APP_ID, this.source.getAppId());
    }

    public TablePO getSource() {
        return this.source;
    }

    public String getName() {
        return this.source.getName();
    }

    public String getDbName() {
        return NameUtils.camelToUnderline(this.app.getName()) + "_" + NameUtils.camelToUnderline(this.source.getName());
    }

    public List<TableFieldDefinition> getFields() {
        List<TableFieldDefinition> list = new ArrayList<>();
        this.source.getFields().forEach(field -> list.add(new TableFieldDefinition(field)));
        return list;
    }

    public void eachFields(Consumer<TableFieldDefinition> fieldProxyConsumer) {
        List<TableFieldPO> fields = this.source.getFields();
        if (fields == null || fields.isEmpty()) {
            throw new TableException("filed is empty");
        }
        fields.forEach(field -> fieldProxyConsumer.accept(new TableFieldDefinition(field)));
    }

    public String getPrimaryFieldName() {
        // 默认所有主键都是id
        return "id";
    }

    public Boolean hasField(String fieldName) {
        for (TableFieldPO field : this.source.getFields()) {
            if (StringUtils.equals(fieldName, field.getName())) {
                return true;
            }
        }
        return false;
    }

    public TableFieldDefinition getField(String fieldName) {
        for (TableFieldPO field : this.source.getFields()) {
            if (StringUtils.equals(fieldName, field.getName())) {
                return new TableFieldDefinition(field);
            }
        }
        return null;
    }
}
