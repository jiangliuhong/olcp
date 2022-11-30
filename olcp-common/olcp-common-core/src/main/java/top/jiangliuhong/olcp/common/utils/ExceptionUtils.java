package top.jiangliuhong.olcp.common.utils;

import org.apache.commons.lang3.StringUtils;

public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static String getExceptionMessage(Exception exception) {
        String message = exception.getMessage();
        if (StringUtils.isBlank(message)) {
            Throwable cause = exception.getCause();
            if (cause != null) {
                message = cause.getMessage();
            }
        }
        return message;
    }
}
