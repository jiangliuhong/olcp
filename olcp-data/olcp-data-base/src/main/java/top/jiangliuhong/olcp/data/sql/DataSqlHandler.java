package top.jiangliuhong.olcp.data.sql;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@Component
public class DataSqlHandler {

    private final Map<String, IDataSqlHandler> handlerMap = new HashMap<>();
    private String defaultType = "mysql8";

    public DataSqlHandler(List<IDataSqlHandler> handlers) {
        handlers.forEach(handler -> {
            if (StringUtils.isBlank(handler.type())) {
                return;
            }
            if (this.handlerMap.containsKey(handler.type())) {
                log.warning("IDataSqlHandler type duplication for " + handler.type());
                return;
            }
            this.handlerMap.put(handler.type(), handler);
        });
    }

    public IDataSqlHandler handler() {
        return this.handlerMap.get(this.defaultType);
    }
}
