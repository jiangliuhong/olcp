package top.jiangliuhong.olcp.data.entity;

import java.util.Map;
import java.util.Set;

public interface EntityValue extends Map<String, Object> {

    EntityValue genId();

    String getName();

    Map<String, Object> getMap();

    Object get(String name);

    EntityValue set(String name, Object value);

    EntityValue setAll(Map<String, Object> values);

    String getPrimary();

    Set<String> getColumnNames();

    EntityValue create();

    EntityValue update();

    EntityValue delete();

    EntityValue createOrUpdate();


}
