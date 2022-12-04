package top.jiangliuhong.olcp.data.entity.condition;

import top.jiangliuhong.olcp.data.entity.EntityCondition;
import top.jiangliuhong.olcp.data.entity.EntityConditionBuilder;

public abstract class AbstractEntityCondition implements EntityCondition {

    @Override
    public void accept(EntityConditionBuilder builder) {
        builder.accept(this);
    }
}
