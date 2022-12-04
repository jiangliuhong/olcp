package top.jiangliuhong.olcp.data.entity;

import lombok.extern.log4j.Log4j2;
import top.jiangliuhong.olcp.common.utils.IdUtils;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.context.TableExecutionContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j2
public class TableEntityValueImpl implements EntityValue {

    private final TableDefinition definition;
    private final StringObjectMap valueMapInternal;
    private final TableExecutionContext context;
    private final TableEntity entity;

    public TableEntityValueImpl(TablePO definition, TableEntity entity, TableExecutionContext context) {
        this.entity = entity;
        this.definition = new TableDefinition(definition);
        this.valueMapInternal = new StringObjectMap(this.definition.getFields().size());
        this.context = context;
    }

    public TableEntityValueImpl(TableDefinition definition, TableEntity entity, TableExecutionContext context) {
        this.entity = entity;
        this.definition = definition;
        this.valueMapInternal = new StringObjectMap(this.definition.getFields().size());
        this.context = context;
    }

    @Override
    public EntityValue genId() {
        this.put(this.definition.getPrimaryFieldName(), IdUtils.generate());
        return this;
    }

    @Override
    public String getName() {
        return this.definition.getDbName();
    }

    @Override
    public Map<String, Object> getMap() {
        return this.valueMapInternal;
    }

    @Override
    public Object get(String name) {
        return this.valueMapInternal.get(name);
    }

    @Override
    public EntityValue set(String name, Object value) {
        this.valueMapInternal.put(name, value);
        return this;
    }

    @Override
    public EntityValue setAll(Map<String, Object> values) {
        this.valueMapInternal.putAll(values);
        return this;
    }

    @Override
    public String getPrimary() {
        return this.valueMapInternal.get(this.definition.getPrimaryFieldName()).toString();
    }

    @Override
    public Set<String> getColumnNames() {
        Set<String> names = new HashSet<>();
        this.definition.eachFields(field -> {
            names.add(field.getName());
        });
        return names;
    }

    @Override
    public EntityValue create() {
        StringObjectMap createResult = this.context.create(this.definition, this.valueMapInternal);
        this.setAll(createResult);
        return this;
    }

    @Override
    public EntityValue update() {
        // TODO
        return null;
    }

    @Override
    public EntityValue delete() {
        // TODO
        return null;
    }

    @Override
    public EntityValue createOrUpdate() {
        // TODO
        return null;
    }

    // hash method

    @Override
    public int size() {
        return this.valueMapInternal.size();
    }

    @Override
    public boolean isEmpty() {
        return this.valueMapInternal.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.valueMapInternal.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.valueMapInternal.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.valueMapInternal.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.valueMapInternal.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.valueMapInternal.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.valueMapInternal.putAll(m);
    }

    @Override
    public void clear() {
        this.valueMapInternal.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.valueMapInternal.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.valueMapInternal.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.valueMapInternal.entrySet();
    }
}
