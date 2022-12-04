package top.jiangliuhong.olcp.data.entity;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import top.jiangliuhong.olcp.common.utils.StringMap;
import top.jiangliuhong.olcp.data.entity.condition.ConditionField;
import top.jiangliuhong.olcp.data.entity.condition.FieldValueCondition;
import top.jiangliuhong.olcp.data.entity.condition.ListCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class EntityConditionFactoryImpl implements EntityConditionFactory {

    private static final String MAP_JOIN_STR = "_join";
    private static final String MAP_COMP_STR = "_comp";

    @Override
    public EntityCondition makeCondition(EntityCondition lhs, JoinOperator operator, EntityCondition rhs) {
        if (lhs instanceof ListCondition && ((ListCondition) lhs).getOperator().equals(operator)) {
            ((ListCondition) lhs).addCondition(rhs);
            return lhs;
        }
        if (rhs instanceof ListCondition && ((ListCondition) rhs).getOperator().equals(operator)) {
            ((ListCondition) rhs).addCondition(lhs);
            return rhs;
        }
        return new ListCondition(operator, lhs, rhs);
    }

    @Override
    public EntityCondition makeCondition(String fieldName, Object value) {
        return makeCondition(fieldName, null, value);
    }

    @Override
    public EntityCondition makeCondition(String fieldName, ComparisonOperator operator, Object value) {
        return new FieldValueCondition(fieldName, operator, value);
    }

    @Override
    public EntityCondition makeCondition(String fieldName, ComparisonOperator operator, Object value, boolean orNull) {
        EntityCondition condition = makeCondition(fieldName, operator, value);
        if (!orNull) {
            return condition;
        }
        EntityCondition nullCondition = makeCondition(fieldName, ComparisonOperator.IS_NULL, null);
        return makeCondition(condition, JoinOperator.OR, nullCondition);
    }

    @Override
    public EntityCondition makeConditionToField(String fieldName, ComparisonOperator operator, String toFieldName) {
        return makeCondition(fieldName, operator, new ConditionField(toFieldName));
    }

    @Override
    public EntityCondition makeCondition(List<EntityCondition> conditionList) {
        return makeCondition(conditionList, JoinOperator.AND);
    }

    @Override
    public EntityCondition makeCondition(List<EntityCondition> conditionList, JoinOperator operator) {
        if (conditionList == null || conditionList.isEmpty()) {
            throw new RuntimeException("entity condition list can not be empty");
        }
        List<EntityCondition> newConditionList = new ArrayList<>(conditionList);
        if (newConditionList.size() == 1) {
            return newConditionList.get(0);
        } else {
            return new ListCondition(operator, newConditionList);
        }
    }

    @Override
    public EntityCondition makeCondition(List<Object> conditionList, String listOperator,
                                         String mapComparisonOperator, String mapJoinOperator) {
        if (conditionList == null || conditionList.isEmpty()) {
            throw new RuntimeException("Object condition list can not be empty");
        }
        JoinOperator listJoin = JoinOperator.getJoinOperator(listOperator);
        ComparisonOperator mapComparison = ComparisonOperator.getComparisonOperator(mapComparisonOperator);
        JoinOperator mapJoin = JoinOperator.getJoinOperator(mapJoinOperator);
        List<EntityCondition> newConditionList = new ArrayList<>();
        for (Object condition : conditionList) {
            if (condition instanceof EntityCondition) {
                newConditionList.add((EntityCondition) condition);
                continue;
            }
            if (condition instanceof StringMap) {
                StringMap<Object> conditionMap = (StringMap) condition;
                if (conditionMap.isEmpty()) {
                    continue;
                }
                EntityCondition mapEntityCondition = makeCondition(conditionMap, mapComparison, mapJoin);
                newConditionList.add(mapEntityCondition);
                continue;
            }
            throw new RuntimeException("the condition list parameter must contain only Map and EntityCondition objects,found entry of type [" + condition.getClass().getName() + "]");
        }
        if (newConditionList.size() == 1) {
            return newConditionList.get(0);
        } else {
            return new ListCondition(listJoin, newConditionList);
        }
    }

    @Override
    public EntityCondition makeCondition(StringMap<Object> fieldMap, ComparisonOperator comparisonOperator,
                                         JoinOperator joinOperator) {
        if (comparisonOperator == null) {
            comparisonOperator = ComparisonOperator.EQUALS;
        }
        if (joinOperator == null) {
            joinOperator = JoinOperator.AND;
        }
        List<KeyValue<String, Object>> values = new ArrayList<>();
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.startsWith("_")) {
                if (MAP_JOIN_STR.equals(key)) {
                    joinOperator = JoinOperator.getJoinOperator(value.toString());
                } else if (MAP_COMP_STR.equals(key)) {
                    comparisonOperator = ComparisonOperator.getComparisonOperator(value.toString());
                } else {
                    log.warn("key[" + key + "] is wrong");
                }
            } else {
                values.add(new DefaultKeyValue<>(key, value));
            }
        }
        if (values.isEmpty()) {
            throw new RuntimeException("the map filed value is empty");
        }
        List<EntityCondition> conditionList = new ArrayList<>();
        for (KeyValue<String, Object> keyValue : values) {
            EntityCondition condition = makeCondition(keyValue.getKey(), comparisonOperator, keyValue.getValue());
            conditionList.add(condition);
        }
        if (conditionList.size() == 1) {
            return conditionList.get(0);
        } else {
            return new ListCondition(joinOperator, conditionList);
        }
    }

    @Override
    public EntityCondition makeCondition(StringMap<Object> fieldMap) {
        return makeCondition(fieldMap, ComparisonOperator.EQUALS, JoinOperator.AND);
    }

}
