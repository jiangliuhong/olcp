package top.jiangliuhong.olcp.data.utils;

import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;

public final class FieldTypeUtils {

    public static String transformFieldType(TableFieldPO field) {
        switch (field.getType()) {
            case Integer:
                return "int";
            case BigInteger:
                return "bigint";
            case Float:
                return String.format(
                        "float(%s,%s)",
                        field.getMaxLength() > 0 ? field.getMaxLength() : 10,
                        field.getMaxPrecision() >= 0 ? field.getMaxPrecision() : 2
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
                        field.getMaxLength() > 0 ? field.getMaxLength() : 255
                );
        }
    }

}
