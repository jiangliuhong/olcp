package top.jiangliuhong.olcp.web.handler;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<Object> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return ApiResult.fail(e.getStatus(), e.getData(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResult.fail(ApiResultStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
    }

}
