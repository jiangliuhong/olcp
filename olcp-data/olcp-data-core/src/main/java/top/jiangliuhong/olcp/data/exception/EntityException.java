package top.jiangliuhong.olcp.data.exception;

import top.jiangliuhong.olcp.common.exception.BusinessException;

public class EntityException extends BusinessException {

    public EntityException(String message) {
        super(message);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
