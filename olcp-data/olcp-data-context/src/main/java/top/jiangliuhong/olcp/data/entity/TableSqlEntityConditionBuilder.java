package top.jiangliuhong.olcp.data.entity;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.component.TableFieldDefinition;
import top.jiangliuhong.olcp.data.entity.condition.FieldValueCondition;
import top.jiangliuhong.olcp.data.entity.condition.ListCondition;
import top.jiangliuhong.olcp.data.exception.TableException;

import java.util.*;

/**
 * 解析条件
 */
public class TableSqlEntityConditionBuilder implements EntityConditionBuilder {

    private final TableDefinition definition;
    private final StringBuilder builder = new StringBuilder();
    private final List<Object> parameters = new ArrayList<>();

    public TableSqlEntityConditionBuilder(TableDefinition definition) {
        this.definition = definition;
    }

    @Override
    public void accept(EntityCondition condition) {
        ResolveResult resolve = this.resolve(condition);
        this.setBuilder(resolve.getBuilder());
        this.addParameters(resolve.getParameters());
    }

    @Getter
    @Setter
    static class ResolveResult {
        private StringBuilder builder = new StringBuilder();
        private List<Object> parameters = new ArrayList<>();
    }

    private ResolveResult resolve(EntityCondition condition) {
        ResolveResult resolveResult = null;
        if (condition instanceof ListCondition) {
            resolveResult = this.resolve((ListCondition) condition);
        } else if (condition instanceof FieldValueCondition) {
            resolveResult = this.resolve((FieldValueCondition) condition);
        }
        return resolveResult;
    }

    private ResolveResult resolve(ListCondition listCondition) {

        List<EntityCondition> conditionList = listCondition.getConditionList();
        if (conditionList.isEmpty()) {
            return null;
        }
        JoinOperator operator = listCondition.getOperator();
        String operatorString = JoinOperator.getJoinOperatorString(operator);
        int size = conditionList.size();
        if (size == 1) {
            EntityCondition condition = conditionList.get(0);
            return resolve(condition);
        } else {
            ResolveResult resolveResult = new ResolveResult();
            for (int i = 0; i < size; i++) {
                EntityCondition condition = conditionList.get(i);
                ResolveResult resolve = resolve(condition);
                if (resolve == null) {
                    continue;
                }
                resolveResult.getBuilder().append("(").append(resolve.getBuilder()).append(")");
                if (i < size - 1) {
                    resolveResult.getBuilder().append(" ").append(operatorString).append(" ");
                }
                resolveResult.getParameters().addAll(resolve.getParameters());
            }
            return resolveResult;
        }
    }

    private ResolveResult resolve(FieldValueCondition condition) {
        ResolveResult resolveResult = new ResolveResult();
        StringBuilder builder = resolveResult.getBuilder();
        List<Object> parameters = resolveResult.getParameters();
        String fieldName = condition.getField();
        TableFieldDefinition field = this.definition.getField(fieldName);
        if (field == null) {
            throw new TableException("Table " + this.definition.getName() + " does not have field " + fieldName);
        }
        builder.append(field.getDbName()).append(" ");
        ComparisonOperator operator = condition.getOperator();
        String comparisonOperatorString = ComparisonOperator.getComparisonOperatorString(operator);
        Object value = condition.getValue();
        switch (operator) {
            default:
            case EQUALS:
            case NOT_EQUAL:
            case LESS_THAN:
            case GREATER_THAN:
            case LESS_THAN_EQUAL_TO:
            case GREATER_THAN_EQUAL_TO:
                builder.append(comparisonOperatorString).append(" ? ");
                parameters.add(value);
                break;
            case IN:
            case NOT_IN:
                builder.append(comparisonOperatorString).append("(");
                if (value instanceof Collection) {
                    Collection<?> collection = (Collection<?>) value;
                    int valueSize = collection.size();
                    Iterator<?> collectionIterator = collection.iterator();
                    int collectionIndex = 0;
                    while (collectionIterator.hasNext()) {
                        parameters.add(collectionIterator.next());
                        builder.append("?");
                        collectionIndex++;
                        if (collectionIndex != valueSize) {
                            builder.append(",");
                        }
                    }
                } else {
                    builder.append("?");
                    parameters.add(value);
                }
                builder.append(")");
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                if (value instanceof Object[]) {
                    if (((Object[]) value).length < 2) {
                        throw new RuntimeException("the BETWEEN or NOT BETWEEN value size must be 2");
                    }
                    parameters.add(((Object[]) value)[0]);
                    parameters.add(((Object[]) value)[1]);
                } else if (value instanceof List) {
                    if (((List<?>) value).size() < 2) {
                        throw new RuntimeException("the BETWEEN or NOT BETWEEN value size must be 2");
                    }
                    parameters.add(((List<?>) value).get(0));
                    parameters.add(((List<?>) value).get(1));
                } else {
                    throw new RuntimeException("the BETWEEN or NOT BETWEEN value must be Array or List");
                }
                builder.append(comparisonOperatorString);
                builder.append(" ? AND ? ");
                break;
            case LIKE:
            case NOT_LIKE:
                builder.append(comparisonOperatorString).append("'%?%'");
                parameters.add(value);
                break;
            case IS_NULL:
            case IS_NOT_NULL:
                builder.append(comparisonOperatorString);
                break;
        }
        return resolveResult;
    }

    public boolean hasCondition() {
        return this.builder.length() > 0;
    }

    public String getSql() {
        return this.builder.toString();
    }

    public StringBuilder getBuilder() {
        return this.builder;
    }

    public void setBuilder(String builder) {
        this.builder.append(builder);
    }

    public void setBuilder(StringBuilder builder) {
        this.builder.append(builder);
    }

    public List<Object> getParameters() {
        return this.parameters;
    }

    public void addParameters(List<Object> objects) {
        this.parameters.addAll(objects);
    }

    public void addParameters(Object... objects) {
        this.parameters.addAll(Arrays.asList(objects));
    }

    public void addParameters(Integer index, Object... objects) {
        for (Object object : objects) {
            this.parameters.add(index, object);
            index++;
        }
    }
}
