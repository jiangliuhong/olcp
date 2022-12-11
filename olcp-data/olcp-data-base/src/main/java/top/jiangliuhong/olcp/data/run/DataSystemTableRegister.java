package top.jiangliuhong.olcp.data.run;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.annotation.OlcpField;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.type.EngineType;
import top.jiangliuhong.olcp.data.type.FieldType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DataSystemTableRegister {


    private final SystemTableProperties properties;

    private final static String REFERENCE_START = "Reference.";

    public DataSystemTableRegister(SystemTableProperties properties) {
        this.properties = properties;
    }

    public List<TablePO> getRegisterBean() {
        List<TablePO> list = new ArrayList<>();
        properties.getTables().forEach(item -> {
            TablePO sysTable = new TablePO();
            sysTable.setName(item.getName());
            sysTable.setTitle(item.getTitle());
            sysTable.setId(DataSystemAppRegister.SYS_APP_NAME + "." + item.getName());
            sysTable.setVirtualTable(false);
            sysTable.setEngineType(EngineType.InnoDB);
            sysTable.setFields(getFields(sysTable.getId(), sysTable.getAppId(), item.getClazz()));
            sysTable.setAppId(DataSystemAppRegister.SYS_APP_ID);
            list.add(sysTable);
        });
        return list;
    }

    private List<TableFieldPO> getFields(String tableId, String appId, String className) {
        try {
            List<TableFieldPO> list = new ArrayList<>();
            if (StringUtils.isBlank(className)) {
                return list;
            }
            Class<?> clazz = Class.forName(className);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                TableFieldPO tableField = new TableFieldPO();
                tableField.setName(name);
                tableField.setId(tableId + "." + name);
                tableField.setTableId(tableId);
                tableField.setAppId(appId);
                OlcpField olcpFieldAnnotation = field.getAnnotation(OlcpField.class);
                if (olcpFieldAnnotation != null) {
                    if (StringUtils.isNotBlank(olcpFieldAnnotation.title())) {
                        tableField.setTitle(olcpFieldAnnotation.title());
                    } else {
                        tableField.setTitle(name);
                    }
                    String typeString = olcpFieldAnnotation.type() != null ? olcpFieldAnnotation.type() : "String";
                    if (StringUtils.startsWith(typeString, REFERENCE_START)) {
                        String referenceId = typeString.substring(REFERENCE_START.length());
                        tableField.setType(FieldType.Reference);
                        tableField.setReferenceTableId(referenceId);
                    } else {
                        tableField.setType(FieldType.valueOf(typeString));
                    }
                } else {
                    tableField.setTitle(name);
                    tableField.setType(FieldType.String);
                }
                // TODO 是否需要设置长度
                list.add(tableField);
            }
            return list;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
