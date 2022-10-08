package top.jiangliuhong.olcp.common.bean;

import lombok.Data;
import top.jiangliuhong.olcp.common.consts.ApiResultStatus;
import top.jiangliuhong.olcp.common.consts.IApiResultStatus;

/**
 * api result body
 *
 * @param <T>
 */
@Data
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;

    private ApiResult(IApiResultStatus resultStatus, T data) {
        this.code = resultStatus.code();
        this.message = resultStatus.message();
        this.data = data;
    }

    private ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResult<Void> success() {
        return new ApiResult<Void>(ApiResultStatus.SUCCESS, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(ApiResultStatus.SUCCESS, data);
    }

    public static ApiResult<Void> fail(IApiResultStatus resultStatus) {
        return new ApiResult<Void>(resultStatus, null);
    }

    public static <T> ApiResult<T> fail(IApiResultStatus resultStatus, T data) {
        return new ApiResult<T>(resultStatus, data);
    }


}
