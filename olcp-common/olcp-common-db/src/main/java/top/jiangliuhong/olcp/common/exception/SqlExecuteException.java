package top.jiangliuhong.olcp.common.exception;

import top.jiangliuhong.olcp.common.consts.ApiResultStatus;

public class SqlExecuteException extends BusinessException {
    public SqlExecuteException(String message) {
        super(message);
    }

    public SqlExecuteException(ApiResultStatus status, String message) {
        super(status, message);
    }

    public SqlExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlExecuteException(Throwable cause) {
        super(cause);
    }

    public SqlExecuteException(ApiResultStatus status, String message, Object data) {
        super(status, message, data);
    }

    public SqlExecuteException(ApiResultStatus status, String message, Object data, Throwable cause) {
        super(status, message, data, cause);
    }
}
