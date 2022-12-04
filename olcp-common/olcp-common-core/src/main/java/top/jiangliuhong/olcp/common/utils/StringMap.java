package top.jiangliuhong.olcp.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringMap<V> extends HashMap<String, V> implements Map<String, V> {
    public StringMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public StringMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringMap() {
    }

    public StringMap(Map<? extends String, ? extends V> m) {
        super(m);
    }
}
