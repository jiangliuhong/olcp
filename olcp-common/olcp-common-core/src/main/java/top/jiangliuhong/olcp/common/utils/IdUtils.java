package top.jiangliuhong.olcp.common.utils;

import java.util.UUID;

public final class IdUtils {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
