package top.jiangliuhong.olcp.data.exception;

import top.jiangliuhong.olcp.common.consts.ApiResultStatus;
import top.jiangliuhong.olcp.common.exception.BusinessException;

public class AppException extends BusinessException {

    public AppException(String message) {
        super(message);
    }

    public AppException(ApiResultStatus status, String message) {
        super(status, message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(ApiResultStatus status, String message, Object data) {
        super(status, message, data);
    }

    public AppException(ApiResultStatus status, String message, Object data, Throwable cause) {
        super(status, message, data, cause);
    }
}
