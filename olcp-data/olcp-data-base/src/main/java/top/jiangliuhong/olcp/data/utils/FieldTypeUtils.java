package top.jiangliuhong.olcp.data.utils;

import top.jiangliuhong.olcp.common.utils.DateUtils;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.type.FieldType;

import java.sql.Timestamp;
import java.util.Date;

public final class FieldTypeUtils {

    /**
     * 获取字段数据库类型
     *
     * @param field 字段
     * @return 数据库类型
     */
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
                return "datetime";
            case Timestamp:
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

    /**
     * 转换数据库语句需要的字符串
     *
     * @param value 值
     * @param type  类型
     * @return 字符串
     */
    public static Object transformDatabaseString(Object value, FieldType type) {
        if (value == null) {
            return null;
        }
        switch (type) {
            case Integer:
            case BigInteger:
            case Float:
                return value.toString();
            case SysId:
            case Reference:
                return "'" + value + "'";
            case Date:
                return "'" + DateUtils.formatDate((Date) value) + "'";
            case DateTime:
                return "'" + DateUtils.formatDateTime((Date) value) + "'";
            case Timestamp:
                return ((Timestamp) value).getTime();
            case Boolean:
                return (boolean) value ? 1 : 0;
            case Script:
            case Text:
            case String:
            default:
                return "'" + value.toString() + "'";
        }
    }

}
