package top.jiangliuhong.olcp.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.jiangliuhong.olcp.common.bean.ApiResult;
import top.jiangliuhong.olcp.common.consts.ApiResultStatus;
import top.jiangliuhong.olcp.common.exception.BusinessException;

/**
 * webmvc controller exception advice
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<Object> handleBusinessException(BusinessException e) {
        // TODO 记录log日志
        return ApiResult.fail(e.getStatus(), e.getData(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<Object> handleException(Exception e) {
        // TODO 记录log日志
        e.printStackTrace();
        return ApiResult.fail(ApiResultStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
    }

}
