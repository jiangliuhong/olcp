package top.jiangliuhong.olcp.data.script.exception;

import top.jiangliuhong.olcp.common.exception.BusinessException;

public class APIExecuteException extends BusinessException {

    public APIExecuteException(String message) {
        super(message);
    }

    public APIExecuteException(Throwable cause) {
        super(cause);
    }
}
