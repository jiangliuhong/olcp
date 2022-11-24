package top.jiangliuhong.olcp.data.script;

import groovy.lang.GroovyClassLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ScriptRunner implements BeanFactoryPostProcessor {

    private static GroovyClassLoader classLoader;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        GroovyScriptFinder finder = factory.getBean(GroovyScriptFinder.class);
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        ScriptRunner.classLoader = new DataGroovyClassLoader(parent, finder);
    }

    public static GroovyClassLoader getClassLoader() {
        return ScriptRunner.classLoader;
    }

    public static Object runClass(String classname, String method, Object... params) {
        try {
            Class<?> serviceClass = getClassLoader().loadClass(classname);
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
