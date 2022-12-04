package top.jiangliuhong.olcp.data.entity;

import org.apache.commons.lang3.StringUtils;

public enum JoinOperator {
    AND,
    OR;


    public static String getJoinOperatorString(JoinOperator op) {
        return JoinOperator.OR.equals(op) ? "OR" : "AND";
    }

    public static JoinOperator getJoinOperator(String opName) {
        if (StringUtils.isNotBlank(opName)) {
            return AND;
        }
        return "or".equalsIgnoreCase(opName) ? OR : AND;
    }

}
