package top.jiangliuhong.olcp.data.entity;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.context.TableExecutionContext;
import top.jiangliuhong.olcp.data.entity.condition.FieldValueCondition;
import top.jiangliuhong.olcp.data.entity.condition.ListCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TableEntityFindImpl implements EntityFind {
    private final TableDefinition definition;
    private final TableExecutionContext context;
    private final TableEntity entity;
    private List<String> selectFields;
    private List<String> orderFields;
    private EntityCondition whereEntityCondition;
    private EntityCondition havingEntityCondition;
    private Integer offset = 0;
    private Integer limit = 10;

    public TableEntityFindImpl(TablePO table, TableEntity tableEntity, TableExecutionContext context) {
        this.entity = tableEntity;
        this.definition = new TableDefinition(table);
        this.context = context;
    }

    private EntityConditionFactory getConditionFactory() {
        return this.context.getFactory().conditionBuilders();
    }

    @Override
    public EntityValue get(Object primaryValue) {
        EntityCondition condition = getConditionFactory().makeCondition(definition.getPrimaryFieldName(), primaryValue);
        List<StringObjectMap> list = query(condition);
        if (list == null || list.isEmpty()) {
            return null;
        }
        TableEntityValueImpl value = new TableEntityValueImpl(this.definition, this.entity, this.context);
        return value.setAll(list.get(0));
    }

    @Override
    public EntityFind condition(String fieldName, Object value) {
        this.condition(fieldName, ComparisonOperator.EQUALS, value);
        return this;
    }

    @Override
    public EntityFind condition(String fieldName, ComparisonOperator operator, Object value) {
        if (!this.definition.hasField(fieldName)) {
            throw new RuntimeException("Field " + fieldName + " not found on table " + this.definition.getName());
        }
        if (operator == null) {
            operator = ComparisonOperator.EQUALS;
        }
        return condition(new FieldValueCondition(fieldName, operator, value));
    }

    @Override
    public EntityFind condition(String fieldName, String operator, Object value) {
        ComparisonOperator comparisonOperator = ComparisonOperator.getComparisonOperator(operator);
        return condition(fieldName, comparisonOperator, value);
    }

    @Override
    public EntityFind conditionToField(String fieldName, ComparisonOperator operator, String toFieldName) {
        EntityCondition condition = this.context.getFactory().conditionBuilders()
                .makeConditionToField(fieldName, operator, toFieldName);
        return condition(condition);
    }

    @Override
    public EntityFind condition(Map<String, Object> fields) {
        if (fields == null || fields.isEmpty()) {
            return this;
        }
        if (fields instanceof EntityValue) {
            fields = ((EntityValue) fields).getMap();
        }
        int fieldSize = fields.size();
        if (fieldSize == 0) {
            return this;
        }
        fields.forEach(this::condition);
        return this;
    }

    @Override
    public EntityFind condition(EntityCondition condition) {
        if (condition == null) {
            return this;
        }
        if (condition instanceof ListCondition) {
            ListCondition lc = (ListCondition) condition;
            List<EntityCondition> conditionList = lc.getConditionList();
            if (conditionList.isEmpty()) {
                return this;
            }
            if (EntityCondition.AND.equals(lc.getOperator())) {
                for (EntityCondition entityCondition : conditionList) {
                    this.condition(entityCondition);
                }
                return this;
            }
        }

        if (whereEntityCondition == null) {
            whereEntityCondition = condition;
        } else {
            if (whereEntityCondition instanceof ListCondition &&
                    ((ListCondition) whereEntityCondition).getOperator() == EntityCondition.AND) {
                ((ListCondition) whereEntityCondition).addCondition(condition);
            } else {
                List<EntityCondition> condList = new ArrayList<>();
                condList.add(whereEntityCondition);
                condList.add(condition);
                whereEntityCondition = new ListCondition(condList);
            }
        }
        return this;
    }

    @Override
    public EntityFind havingCondition(EntityCondition condition) {
        // TODO
        return null;
    }

    @Override
    public boolean getHasCondition() {
        return false;
    }

    @Override
    public EntityCondition getWhereEntityCondition() {
        return whereEntityCondition;
    }

    @Override
    public EntityCondition getHavingEntityCondition() {
        return null;
    }

    @Override
    public EntityFind selectField(String fieldToSelect) {
        if (StringUtils.isBlank(fieldToSelect)) {
            return this;
        }
        if (this.selectFields == null) {
            this.selectFields = new ArrayList<>();
        }
        buildFieldNameArray(this.selectFields, fieldToSelect);
        return this;
    }

    private void buildFieldNameArray(List<String> array, String fieldToSelect) {
        if (fieldToSelect.contains(",")) {
            String[] fsSplit = fieldToSelect.split(",");
            for (int i = 0; i < fsSplit.length; i++) {
                String selectName = fsSplit[i];
                if (this.definition.hasField(selectName) && !array.contains(selectName)) {
                    array.add(selectName);
                }
            }
        } else {
            if (this.definition.hasField(fieldToSelect) && !array.contains(fieldToSelect))
                array.add(fieldToSelect);
        }
    }

    @Override
    public EntityFind selectFields(Collection<String> fieldsToSelect) {
        if (fieldsToSelect == null || fieldsToSelect.isEmpty()) {
            return this;
        }
        for (String fieldToSelect : fieldsToSelect) {
            this.selectField(fieldToSelect);
        }
        return this;
    }

    @Override
    public List<String> getSelectFields() {
        return this.selectFields;
    }

    @Override
    public EntityFind orderBy(String orderByFieldName) {
        if (StringUtils.isBlank(orderByFieldName)) {
            return this;
        }
        if (this.orderFields == null) {
            this.orderFields = new ArrayList<>();
        }
        buildFieldNameArray(this.orderFields, orderByFieldName);
        return this;
    }

    @Override
    public EntityFind orderBy(List<String> orderByFieldNames) {
        if (orderByFieldNames == null || orderByFieldNames.isEmpty()) {
            return this;
        }
        for (String orderByFieldName : orderByFieldNames) {
            this.orderBy(orderByFieldName);
        }
        return this;
    }

    @Override
    public List<String> getOrderBy() {
        return this.orderFields;
    }

    @Override
    public EntityFind offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public EntityFind offset(int pageIndex, int pageSize) {
        this.offset = (pageIndex - 1) * pageSize;
        this.limit = pageSize;
        return this;
    }

    @Override
    public Integer getOffset() {
        return this.offset;
    }

    @Override
    public EntityFind limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Integer getLimit() {
        return this.limit;
    }

    @Override
    public int getPageIndex() {
        return this.offset / this.limit + 1;
    }

    @Override
    public int getPageSize() {
        return this.limit;
    }

    @Override
    public EntityList query() {
        List<StringObjectMap> list = query(this.getWhereEntityCondition());
        if (list == null || list.isEmpty()) {
            return new EntityListImpl();
        }
        EntityListImpl entityList = new EntityListImpl();
        for (StringObjectMap map : list) {
            EntityValue value = new TableEntityValueImpl(this.definition, this.entity, this.context);
            value.setAll(map);
            entityList.add(value);
        }
        return entityList;
    }

    private String getSelectFieldsString() {
        List<String> selectFields = this.getSelectFields();
        if (selectFields == null || selectFields.isEmpty()) {
            selectFields = new ArrayList<>();
            List<String> finalSelectFields = selectFields;
            this.definition.eachFields(field -> {
                finalSelectFields.add(field.getDbName());
            });
        }
        return StringUtils.join(selectFields, ",");
    }


    private List<StringObjectMap> query(EntityCondition condition) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        String selectFieldsString = this.getSelectFieldsString();
        queryBuilder.append(selectFieldsString);
        queryBuilder.append(" FROM ").append(definition.getDbName()).append(" ");
        List<Object> parameters = null;
        if (condition != null) {
            TableSqlEntityConditionBuilder builder = new TableSqlEntityConditionBuilder(this.definition);
            condition.accept(builder);
            if (builder.hasCondition()) {
                queryBuilder.append(" WHERE ");
                queryBuilder.append(builder.getBuilder());
                parameters = builder.getParameters();
            }
        }
        if (parameters != null && !parameters.isEmpty()) {
            return this.context.query(this.definition, queryBuilder.toString(), parameters.toArray());
        } else {
            return this.context.query(this.definition, queryBuilder.toString());
        }
    }
}
