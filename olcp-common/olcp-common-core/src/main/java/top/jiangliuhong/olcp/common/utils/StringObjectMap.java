package top.jiangliuhong.olcp.common.utils;

import java.util.Map;

public class StringObjectMap extends StringMap<Object> {
    public StringObjectMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public StringObjectMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringObjectMap() {
    }

    public StringObjectMap(Map<? extends String, ?> m) {
        super(m);
    }
}
