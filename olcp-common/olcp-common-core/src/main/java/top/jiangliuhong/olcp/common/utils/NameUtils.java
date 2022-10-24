package top.jiangliuhong.olcp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NameUtils {

    /**
     * Underline Case to Camel Case<br>
     * example: user_id -> userId
     *
     * @param underline
     * @return
     */
    public static String underlineToCamel(String underline) {
        String[] ss = underline.split("_");
        if (ss.length == 1) {
            return underline;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ss[0]);
        for (int i = 1; i < ss.length; i++) {
            sb.append(upperFirstCase(ss[i]));
        }
        return sb.toString();
    }

    /**
     * Camel Case to Underline Case<br>
     * example:userId -> user_id
     *
     * @param camel
     * @return
     */
    public static String camelToUnderline(String camel) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(camel);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    private static String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    private static String upperFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}
