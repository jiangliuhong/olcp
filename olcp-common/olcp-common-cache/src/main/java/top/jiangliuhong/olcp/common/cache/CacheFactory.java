package top.jiangliuhong.olcp.common.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheFactory {

    private Map<String, ICacheFactory> factories;

    public CacheFactory(List<ICacheFactory> factories) {
        this.factories = new HashMap<>();
        for (ICacheFactory factory : factories) {
            this.factories.put(factory.name(), factory);
        }
    }

    public ICacheFactory getFactory(String name) {
        return this.factories.get(name);
    }
}
