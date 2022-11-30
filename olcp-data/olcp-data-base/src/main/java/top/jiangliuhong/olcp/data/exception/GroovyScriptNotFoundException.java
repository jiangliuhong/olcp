package top.jiangliuhong.olcp.data.exception;

import top.jiangliuhong.olcp.common.exception.BusinessException;

public class GroovyScriptNotFoundException extends BusinessException {

    public GroovyScriptNotFoundException(String message) {
        super(message);
    }

    public GroovyScriptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
