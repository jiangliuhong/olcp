package top.jiangliuhong.olcp.data.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.jiangliuhong.olcp.common.consts.SystemDataConst;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.type.FieldType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "olcp.data.system.base")
public class SystemTableProperties {

    private List<Table> tables = new ArrayList<>();
    private List<TableFieldDO> defaultFields = new ArrayList<>();

    public void setTables(List<Table> tables) {
        if (tables != null) {
            for (Table table : tables) {
                if (StringUtils.isBlank(table.getAppId())) {
                    table.setAppId(SystemDataConst.SYSTEM_NAME);
                }
            }
        }
        this.tables = tables;
    }

    public void setDefaultFields(List<TableFieldDO> defaultFields) {
        if (defaultFields != null) {
            for (TableFieldDO defaultField : defaultFields) {
                if (defaultField.getType() == null) {
                    defaultField.setType(FieldType.String);
                }
                if (StringUtils.isBlank(defaultField.getAppId())) {
                    defaultField.setAppId(SystemDataConst.SYSTEM_NAME);
                }
                defaultField.setSystemField(true);
            }
        }
        this.defaultFields = defaultFields;
    }

    @Getter
    @Setter
    public static class Table {
        private String name;
        private String title;
        private String appId;
        private String clazz;
    }
}
