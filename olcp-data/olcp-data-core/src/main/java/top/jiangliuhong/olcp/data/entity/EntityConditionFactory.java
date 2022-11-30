package top.jiangliuhong.olcp.data.entity;

import java.util.List;
import java.util.Map;

public interface EntityConditionFactory {

    EntityCondition getTrueCondition();

    EntityCondition makeCondition(EntityCondition lhs, JoinOperator operator, EntityCondition rhs);

    EntityCondition makeCondition(String fieldName, ComparisonOperator operator, Object value);

    EntityCondition makeCondition(String fieldName, ComparisonOperator operator, Object value, boolean orNull);

    EntityCondition makeConditionToField(String fieldName, ComparisonOperator operator, String toFieldName);

    EntityCondition makeCondition(List<EntityCondition> conditionList);

    EntityCondition makeCondition(List<EntityCondition> conditionList, JoinOperator operator);

    EntityCondition makeCondition(List<Object> conditionList, String listOperator, String mapComparisonOperator, String mapJoinOperator);

    EntityCondition makeCondition(Map<String, Object> fieldMap, ComparisonOperator comparisonOperator, JoinOperator joinOperator);

    EntityCondition makeCondition(Map<String, Object> fieldMap);


}
