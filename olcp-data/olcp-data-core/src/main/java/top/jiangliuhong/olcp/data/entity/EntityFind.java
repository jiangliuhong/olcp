package top.jiangliuhong.olcp.data.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EntityFind {

    EntityValue get(Object primaryValue);

    EntityFind condition(String fieldName, Object value);

    EntityFind condition(String fieldName, ComparisonOperator operator, Object value);

    EntityFind condition(String fieldName, String operator, Object value);

    EntityFind conditionToField(String fieldName, ComparisonOperator operator, String toFieldName);

    EntityFind condition(Map<String, Object> fields);

    EntityFind condition(EntityCondition condition);

    EntityFind havingCondition(EntityCondition condition);

    boolean getHasCondition();

    EntityCondition getWhereEntityCondition();

    EntityCondition getHavingEntityCondition();

    EntityFind selectField(String fieldToSelect);

    EntityFind selectFields(Collection<String> fieldsToSelect);

    List<String> getSelectFields();

    EntityFind orderBy(String orderByFieldName);

    EntityFind orderBy(List<String> orderByFieldNames);

    List<String> getOrderBy();

    EntityFind offset(Integer offset);

    EntityFind offset(int pageIndex, int pageSize);

    Integer getOffset();

    EntityFind limit(Integer limit);

    Integer getLimit();

    int getPageIndex();

    int getPageSize();

    EntityList query();
}
