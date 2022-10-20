package top.jiangliuhong.olcp.common.bean;

import org.apache.commons.lang3.StringUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.jiangliuhong.olcp.common.consts.ApiResultStatus;
import top.jiangliuhong.olcp.common.consts.IApiResultStatus;

/**
 * api result body
 *
 * @param <T>
 */
@Data
@Schema(title = "统一结果对象")
public class ApiResult<T> {
    @Schema(title = "状态码")
    private Integer code;
    @Schema(title = "错误信息")
    private String message;
    @Schema(title = "接口数据")
    private T data;

    private ApiResult(IApiResultStatus resultStatus, T data) {
        this.code = resultStatus.code();
        this.message = resultStatus.message();
        this.data = data;
    }

    private ApiResult(IApiResultStatus resultStatus, T data, String message) {
        this.code = resultStatus.code();
        this.message = StringUtils.isBlank(message) ? resultStatus.message() : message;
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
        return new ApiResult<Void>(resultStatus, null, null);
    }

    public static <T> ApiResult<T> fail(IApiResultStatus resultStatus, T data, String message) {
        return new ApiResult<T>(resultStatus, data, message);
    }

}
