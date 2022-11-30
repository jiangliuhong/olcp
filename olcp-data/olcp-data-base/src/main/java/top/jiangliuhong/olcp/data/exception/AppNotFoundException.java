package top.jiangliuhong.olcp.data.exception;

public class AppNotFoundException extends AppException {

    public AppNotFoundException(String message) {
        super(message);
    }

    public AppNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
