package top.jiangliuhong.olcp.data.entity.condition;

import top.jiangliuhong.olcp.data.entity.ComparisonOperator;

import java.util.Collection;

public class FieldValueCondition extends AbstractEntityCondition {

    private final String field;
    private final ComparisonOperator operator;
    private final Object value;

    public FieldValueCondition(String field, ComparisonOperator operator, Object value) {
        this.field = field;
        ComparisonOperator tempOperator = operator != null ? operator : EQUALS;
        if (value instanceof Collection) {
            if (tempOperator == EQUALS) {
                tempOperator = IN;
            } else if (tempOperator == NOT_EQUAL) {
                tempOperator = NOT_IN;
            }
        }
        this.operator = tempOperator;
        this.value = value;
    }

    public String getField() {
        return this.field;
    }

    public ComparisonOperator getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }
}
