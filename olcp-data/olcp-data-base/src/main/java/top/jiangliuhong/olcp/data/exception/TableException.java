package top.jiangliuhong.olcp.data.exception;

import top.jiangliuhong.olcp.common.consts.ApiResultStatus;
import top.jiangliuhong.olcp.common.exception.BusinessException;

public class TableException extends BusinessException {

    public TableException(String message) {
        super(message);
    }

    public TableException(ApiResultStatus status, String message) {
        super(status, message);
    }

    public TableException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableException(Throwable cause) {
        super(cause);
    }

    public TableException(ApiResultStatus status, String message, Object data) {
        super(status, message, data);
    }

    public TableException(ApiResultStatus status, String message, Object data, Throwable cause) {
        super(status, message, data, cause);
    }
}
