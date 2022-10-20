package top.jiangliuhong.olcp.common.consts;

/**
 * api result status
 */
public enum ApiResultStatus implements IApiResultStatus {
    SUCCESS(200, "OK"),
    BAD_REQUEST(400, "Bad Request params exception"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    UNAUTHORIZED(401, "认证异常"),
    FORBIDDEN(403, "没有权限"),
    ;
    private final Integer code;
    private final String message;

    ApiResultStatus(Integer code) {
        this.code = code;
        this.message = null;
    }

    ApiResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
