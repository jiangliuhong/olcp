package top.jiangliuhong.olcp.common.exception;

import top.jiangliuhong.olcp.common.consts.ApiResultStatus;

/**
 * global and base business exception
 */
public class BusinessException extends RuntimeException {

    protected ApiResultStatus status = ApiResultStatus.INTERNAL_SERVER_ERROR;

    protected Object data;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ApiResultStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(ApiResultStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public BusinessException(ApiResultStatus status, String message, Object data, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.data = data;
    }

    public ApiResultStatus getStatus() {
        return this.status;
    }

    public Object getData() {
        return this.data;
    }
}
