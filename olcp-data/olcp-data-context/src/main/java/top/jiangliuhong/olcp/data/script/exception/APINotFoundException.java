package top.jiangliuhong.olcp.data.script.exception;

import top.jiangliuhong.olcp.common.consts.ApiResultStatus;
import top.jiangliuhong.olcp.common.exception.BusinessException;

public class APINotFoundException extends BusinessException {

    public APINotFoundException(String message) {
        super(ApiResultStatus.NOTFOUND, message);
    }

}
