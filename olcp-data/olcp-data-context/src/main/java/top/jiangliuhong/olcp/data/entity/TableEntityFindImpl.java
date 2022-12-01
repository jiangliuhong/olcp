package top.jiangliuhong.olcp.data.entity;

import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.context.TableExecutionContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TableEntityFindImpl implements EntityFind {
    private final TableDefinition table;
    private final TableExecutionContext tableExecutionContext;
    private final TableEntity tableEntity;


    public TableEntityFindImpl(TablePO table, TableEntity tableEntity, TableExecutionContext tableExecutionContext) {
        this.tableEntity = tableEntity;
        this.table = new TableDefinition(table);
        this.tableExecutionContext = tableExecutionContext;
    }

    @Override
    public EntityValue getByPrimary(Object primaryValue) {
        return null;
    }

    @Override
    public EntityFind condition(String fieldName, Object value) {
        return null;
    }

    @Override
    public EntityFind condition(String fieldName, ComparisonOperator operator, Object value) {
        return null;
    }

    @Override
    public EntityFind condition(String fieldName, String operator, Object value) {
        return null;
    }

    @Override
    public EntityFind conditionToField(String fieldName, ComparisonOperator operator, String toFieldName) {
        return null;
    }

    @Override
    public EntityFind condition(Map<String, Object> fields) {
        return null;
    }

    @Override
    public EntityFind condition(EntityCondition condition) {
        return null;
    }

    @Override
    public EntityFind havingCondition(EntityCondition condition) {
        return null;
    }

    @Override
    public boolean getHasCondition() {
        return false;
    }

    @Override
    public EntityCondition getWhereEntityCondition() {
        return null;
    }

    @Override
    public EntityCondition getHavingEntityCondition() {
        return null;
    }

    @Override
    public EntityFind selectField(String fieldToSelect) {
        return null;
    }

    @Override
    public EntityFind selectFields(Collection<String> fieldsToSelect) {
        return null;
    }

    @Override
    public List<String> getSelectFields() {
        return null;
    }

    @Override
    public EntityFind orderBy(String orderByFieldName) {
        return null;
    }

    @Override
    public EntityFind orderBy(List<String> orderByFieldNames) {
        return null;
    }

    @Override
    public List<String> getOrderBy() {
        return null;
    }

    @Override
    public EntityFind offset(Integer offset) {
        return null;
    }

    @Override
    public EntityFind offset(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Integer getOffset() {
        return null;
    }

    @Override
    public EntityFind limit(Integer limit) {
        return null;
    }

    @Override
    public Integer getLimit() {
        return null;
    }

    @Override
    public int getPageIndex() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public EntityList query() {
        return null;
    }
}
