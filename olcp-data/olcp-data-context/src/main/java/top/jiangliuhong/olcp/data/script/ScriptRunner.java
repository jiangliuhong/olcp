package top.jiangliuhong.olcp.data.script;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.exception.GroovyScriptNotFoundException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptRunner implements BeanFactoryPostProcessor {

    private static GroovyScriptFinder finder;
    private static final Map<String, GroovyClassLoader> loaderMap = new HashMap<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        ScriptRunner.finder = factory.getBean(GroovyScriptFinder.class);
    }

    public static Class<?> loadClass(String classname) throws ClassNotFoundException {
        String appName = CacheUtils.getCacheValue(CacheNames.GROOVY_FILE_APP, classname);
        if (StringUtils.isBlank(appName)) {
            throw new GroovyScriptNotFoundException("没有找到:" + classname);
        }
        return getClassLoader(appName).loadClass(classname);
    }


    public static GroovyClassLoader getClassLoader(String appName) {
        if (loaderMap.containsKey(appName)) {
            return loaderMap.get(appName);
        }
        synchronized (ScriptRunner.class) {
            if (!loaderMap.containsKey(appName)) {
                ClassLoader parent = Thread.currentThread().getContextClassLoader();
                AppGroovyScriptFinder finder = new AppGroovyScriptFinder(appName, ScriptRunner.finder);
                GroovyClassLoader loader = new AppDataGroovyClassLoader(parent, finder, appName);
                loaderMap.put(appName, loader);
            }
        }
        return loaderMap.get(appName);
    }

    public static Object runClass(String classname, String method, Object... params) {
        try {
            Class<?> serviceClass = loadClass(classname);
            Object service = serviceClass.getDeclaredConstructor().newInstance();
            List<Class<?>> pts = new ArrayList<>();
            for (Object param : params) {
                pts.add(param.getClass());
            }
            Class<?>[] parameterTypes = pts.toArray(new Class<?>[0]);
            Method serviceMethod = serviceClass.getDeclaredMethod(method, parameterTypes);
            return serviceMethod.invoke(service, params);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
