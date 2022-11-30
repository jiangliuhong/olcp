package top.jiangliuhong.olcp.data.entity.condition;

import top.jiangliuhong.olcp.data.entity.EntityCondition;
import top.jiangliuhong.olcp.data.entity.JoinOperator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public class ListCondition extends AbstractEntityCondition {
    private List<EntityCondition> conditionList = new ArrayList<>();
    private int conditionListSize = 0;
    private JoinOperator operator;

    public ListCondition(List<EntityCondition> conditionList, JoinOperator operator) {
        this.operator = operator != null ? operator : AND;
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
