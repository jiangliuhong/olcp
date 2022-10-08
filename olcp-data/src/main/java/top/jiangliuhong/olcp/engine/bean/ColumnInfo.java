package top.jiangliuhong.olcp.engine.bean;

import lombok.Getter;
import top.jiangliuhong.olcp.engine.load.bean.FieldInfo;

@Getter
public class ColumnInfo {
    private final String columnName;
    private final String defaultValue;
    private final String comment;
    private final boolean required;
    private final String columnType;

    public ColumnInfo(FieldInfo fieldInfo) {
        this.columnName = fieldInfo.getName();
        this.defaultValue = fieldInfo.getDefaultValue();
        this.comment = fieldInfo.getShortDescription();
        this.required = fieldInfo.isRequired();
        this.columnType = resolveColumnType(fieldInfo);
    }

    private String resolveColumnType(FieldInfo fieldInfo) {
        switch (fieldInfo.getType()) {
            case Integer:
                return "int";
            case BigInteger:
                return "bigint";
            case Float:
                return String.format(
                        "float(%s,%s)",
                        fieldInfo.getLength() > 0 ? fieldInfo.getLength() : 10,
                        fieldInfo.getPrecision() >= 0 ? fieldInfo.getPrecision() : 2
                );
            case SysId:
            case Reference:
                return "varchar(32)";
            case Date:
                return "date";
            case DateTime:
                return "timestamp";
            case Script:
            case Text:
                return "text";
            case Boolean:
                return "tinyint(1)";
            case String:
            default:
                return String.format(
                        "varchar(%s)",
                        fieldInfo.getLength() > 0 ? fieldInfo.getLength() : 255
                );
        }
    }
}
