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
        loadClassCache(top.jiangliuhong.olcp.sdk.annotation.Api.class);
        loadClassCache(top.jiangliuhong.olcp.sdk.annotation.Post.class);
        loadClassCache(top.jiangliuhong.olcp.sdk.annotation.Put.class);
        loadClassCache(top.jiangliuhong.olcp.sdk.annotation.Get.class);
        loadClassCache(top.jiangliuhong.olcp.sdk.annotation.Delete.class);
        loadClassCache(top.jiangliuhong.olcp.sdk.function.AbstractApi.class);
    }

    private void loadClassCache(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        this.classCache.put(packageName, clazz);
        String simplePackageName = packageName.replace("top.jiangliuhong.olcp.sdk", "olcp.annotation");
        this.classCache.put(simplePackageName, clazz);
    }
}
