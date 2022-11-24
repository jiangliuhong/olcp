package top.jiangliuhong.olcp.data.script;

import groovy.lang.GroovyClassLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的groovy class loader
 */
public class DataGroovyClassLoader extends GroovyClassLoader {

    private final IGroovyScriptFinder finder;

    private final Map<String, Class<?>> classCache = new HashMap<>();

    public DataGroovyClassLoader(ClassLoader loader, IGroovyScriptFinder finder) {
        super(loader);
        this.finder = finder;
        this.loadClassCache();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> cacheClass = this.classCache.get(name);
        if (cacheClass != null) {
            return cacheClass;
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cacheClass = this.classCache.get(name);
        if (cacheClass != null) {
            return cacheClass;
        }
        String text = this.finder.findByClassname(name);
        if (text != null) {
            return super.parseClass(text);
        } else {
            return super.findClass(name);
        }
    }

    @Override
    protected boolean isRecompilable(Class cls) {
        if (cls == null) {
            return true;
        }
        if (this.finder.isRecompilable(cls.getName())) {
            return true;
        }
        return super.isRecompilable(cls);
    }

    private void loadClassCache() {
        this.classCache.put("olcp.annotation.Api", top.jiangliuhong.olcp.data.script.annotation.Api.class);
        this.classCache.put("olcp.annotation.Post", top.jiangliuhong.olcp.data.script.annotation.Post.class);
        this.classCache.put("olcp.annotation.Put", top.jiangliuhong.olcp.data.script.annotation.Put.class);
        this.classCache.put("olcp.annotation.Get", top.jiangliuhong.olcp.data.script.annotation.Get.class);
        this.classCache.put("olcp.annotation.Delete", top.jiangliuhong.olcp.data.script.annotation.Delete.class);
        this.classCache.put("olcp.function.AbstractApi", top.jiangliuhong.olcp.data.script.function.AbstractApi.class);
    }
}
