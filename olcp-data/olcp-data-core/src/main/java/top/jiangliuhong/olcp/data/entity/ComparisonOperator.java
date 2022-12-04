package top.jiangliuhong.olcp.data.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum ComparisonOperator {
    EQUALS,
    NOT_EQUAL,
    LESS_THAN,
    GREATER_THAN,
    LESS_THAN_EQUAL_TO,
    GREATER_THAN_EQUAL_TO,
    IN,
    NOT_IN,
    BETWEEN,
    NOT_BETWEEN,
    LIKE,
    NOT_LIKE,
    IS_NULL,
    IS_NOT_NULL;

    static final Map<String, ComparisonOperator> stringComparisonOperatorMap = new HashMap<>() {{
        put("=", EQUALS);

        put("equals", EQUALS);
        put("not-equals", NOT_EQUAL);
        put("not-equal", NOT_EQUAL);
        put("!=", NOT_EQUAL);
        put("<>", NOT_EQUAL);

        put("less-than", LESS_THAN);
        put("less", LESS_THAN);
        put("<", LESS_THAN);

        put("greater-than", GREATER_THAN);
        put("greater", GREATER_THAN);
        put(">", GREATER_THAN);

        put("less-than-equal-to", LESS_THAN_EQUAL_TO);
        put("less-equals", LESS_THAN_EQUAL_TO);
        put("<=", LESS_THAN_EQUAL_TO);

        put("greater-than-equal-to", GREATER_THAN_EQUAL_TO);
        put("greater-equals", GREATER_THAN_EQUAL_TO);
        put(">=", GREATER_THAN_EQUAL_TO);

        put("in", IN);
        put("IN", IN);

        put("not-in", NOT_IN);
        put("NOT IN", NOT_IN);

        put("between", BETWEEN);
        put("BETWEEN", BETWEEN);

        put("not-between", NOT_BETWEEN);
        put("NOT BETWEEN", NOT_BETWEEN);

        put("like", LIKE);
        put("LIKE", LIKE);

        put("not-like", NOT_LIKE);
        put("NOT LIKE", NOT_LIKE);

        put("is-null", IS_NULL);
        put("IS NULL", IS_NULL);

        put("is-not-null", IS_NOT_NULL);
        put("IS NOT NULL", IS_NOT_NULL);

    }};

    public static ComparisonOperator getComparisonOperator(String name) {
        if (StringUtils.isBlank(name)) {
            return EQUALS;
        }
        ComparisonOperator comparisonOperator = stringComparisonOperatorMap.get(name);
        return comparisonOperator == null ? EQUALS : comparisonOperator;
    }

    public static final Map<ComparisonOperator, String> comparisonOperatorStringMap = new HashMap<>() {{
        put(ComparisonOperator.EQUALS, "=");
        put(ComparisonOperator.NOT_EQUAL, "!=");
        put(ComparisonOperator.LESS_THAN, "<");
        put(ComparisonOperator.GREATER_THAN, ">");
        put(ComparisonOperator.LESS_THAN_EQUAL_TO, "<=");
        put(ComparisonOperator.GREATER_THAN_EQUAL_TO, ">=");
        put(ComparisonOperator.IN, "IN");
        put(ComparisonOperator.NOT_IN, "NOT IN");
        put(ComparisonOperator.BETWEEN, "BETWEEN");
        put(ComparisonOperator.NOT_BETWEEN, "NOT BETWEEN");
        put(ComparisonOperator.LIKE, "LIKE");
        put(ComparisonOperator.NOT_LIKE, "NOT LIKE");
        put(ComparisonOperator.IS_NULL, "IS NULL");
        put(ComparisonOperator.IS_NOT_NULL, "IS NOT NULL");
    }};

    public static String getComparisonOperatorString(ComparisonOperator operator) {
        if (operator == null) {
            operator = ComparisonOperator.EQUALS;
        }
        return comparisonOperatorStringMap.get(operator);
    }
}
