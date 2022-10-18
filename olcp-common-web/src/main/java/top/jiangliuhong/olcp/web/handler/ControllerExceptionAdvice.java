package top.jiangliuhong.olcp.web.handler;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.jiangliuhong.olcp.common.bean.ApiResult;
import top.jiangliuhong.olcp.common.consts.ApiResultStatus;

@ControllerAdvice
public class ControllerExceptionAdvice {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handleException(Exception e) {
        // TODO 记录log日志
        e.printStackTrace();
        return ApiResult.fail(ApiResultStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
    }

}
