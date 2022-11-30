package top.jiangliuhong.olcp.common.utils;

import java.util.Map;

public class StringObjectMap extends StringMap<Object> {

    public StringObjectMap() {
        super();
    }

    public StringObjectMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringObjectMap(Map<String, Object> cloneMap) {
        super(cloneMap);
    }
}
