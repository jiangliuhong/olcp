package top.jiangliuhong.olcp.common.utils;

import java.util.UUID;

public class IdUtils {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
