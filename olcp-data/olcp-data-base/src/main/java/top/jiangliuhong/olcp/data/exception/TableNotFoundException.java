package top.jiangliuhong.olcp.data.exception;

public class TableNotFoundException extends TableException {

    public TableNotFoundException(String message) {
        super(message);
    }

    public TableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
