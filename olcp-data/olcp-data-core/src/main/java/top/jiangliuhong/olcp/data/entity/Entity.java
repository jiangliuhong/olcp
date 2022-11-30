package top.jiangliuhong.olcp.data.entity;

public interface Entity {

    EntityValue value(String name);

    EntityFind find(String name);

}
