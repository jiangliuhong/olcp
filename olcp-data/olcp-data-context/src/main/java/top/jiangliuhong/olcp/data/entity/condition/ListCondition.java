package top.jiangliuhong.olcp.data.entity.condition;

import top.jiangliuhong.olcp.data.entity.EntityCondition;
import top.jiangliuhong.olcp.data.entity.EntityConditionBuilder;
import top.jiangliuhong.olcp.data.entity.JoinOperator;

import java.util.*;

public class ListCondition extends AbstractEntityCondition {
    private final List<EntityCondition> conditionList = new ArrayList<>();
    private int conditionListSize = 0;
    private final JoinOperator operator;

    public ListCondition(List<EntityCondition> conditionList) {
        this.operator = AND;
        initListCondition(conditionList);
    }

    public ListCondition(JoinOperator operator, EntityCondition... conditions) {
        this.operator = operator != null ? operator : AND;
        List<EntityCondition> conditionList = new ArrayList<>();
        if (conditions != null) {
            conditionList.addAll(Arrays.asList(conditions));
        }
        initListCondition(conditionList);
    }

    public ListCondition(JoinOperator operator, List<EntityCondition> conditionList) {
        this.operator = operator != null ? operator : AND;
        initListCondition(conditionList);
    }

    private void initListCondition(List<EntityCondition> conditionList) {
        if (conditionList != null) {
            conditionListSize = conditionList.size();
            if (conditionListSize > 0) {
                if (conditionList instanceof RandomAccess) {
                    // avoid creating an iterator if possible
                    int listSize = conditionList.size();
                    for (int i = 0; i < listSize; i++) {
                        EntityCondition cond = conditionList.get(i);
                        if (cond != null) {
                            this.conditionList.add(cond);
                        }
                    }
                } else {
                    Iterator<EntityCondition> conditionIter = conditionList.iterator();
                    while (conditionIter.hasNext()) {
                        EntityCondition cond = conditionIter.next();
                        if (cond != null) {
                            this.conditionList.add(cond);
                        }
                    }
                }
            }
        }
    }

    public void addCondition(EntityCondition condition) {
        if (condition != null) {
            conditionList.add(condition);
        }
        conditionListSize = conditionList.size();
    }

    public void addConditions(List<EntityCondition> condList) {
        int condListSize = condList != null ? condList.size() : 0;
        if (condListSize == 0) return;
        for (int i = 0; i < condListSize; i++) {
            addCondition(condList.get(i));
        }
        conditionListSize = conditionList.size();
    }

    public void addConditions(ListCondition listCond) {
        addConditions(listCond.getConditionList());
    }

    public JoinOperator getOperator() {
        return operator;
    }

    public List<EntityCondition> getConditionList() {
        return conditionList;
    }

}
