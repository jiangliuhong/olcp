package top.jiangliuhong.olcp.data.entity;

import java.util.Map;

public interface EntityCondition {

    ComparisonOperator EQUALS = ComparisonOperator.EQUALS;
    ComparisonOperator NOT_EQUAL = ComparisonOperator.NOT_EQUAL;
    ComparisonOperator LESS_THAN = ComparisonOperator.LESS_THAN;
    ComparisonOperator GREATER_THAN = ComparisonOperator.GREATER_THAN;
    ComparisonOperator LESS_THAN_EQUAL_TO = ComparisonOperator.LESS_THAN_EQUAL_TO;
    ComparisonOperator GREATER_THAN_EQUAL_TO = ComparisonOperator.GREATER_THAN_EQUAL_TO;
    ComparisonOperator IN = ComparisonOperator.IN;
    ComparisonOperator NOT_IN = ComparisonOperator.NOT_IN;
    ComparisonOperator BETWEEN = ComparisonOperator.BETWEEN;
    ComparisonOperator NOT_BETWEEN = ComparisonOperator.NOT_BETWEEN;
    ComparisonOperator LIKE = ComparisonOperator.LIKE;
    ComparisonOperator NOT_LIKE = ComparisonOperator.NOT_LIKE;
    ComparisonOperator IS_NULL = ComparisonOperator.IS_NULL;
    ComparisonOperator IS_NOT_NULL = ComparisonOperator.IS_NOT_NULL;

    JoinOperator AND = JoinOperator.AND;
    JoinOperator OR = JoinOperator.OR;

}
