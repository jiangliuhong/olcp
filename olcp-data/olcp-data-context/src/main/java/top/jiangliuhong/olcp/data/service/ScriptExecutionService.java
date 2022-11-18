package top.jiangliuhong.olcp.data.service;

import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.data.script.DataGroovyClassLoader;
import top.jiangliuhong.olcp.data.script.ServiceGroovyScriptFinder;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;

@Service
public class ScriptExecutionService {

    @Autowired
    private ServiceGroovyScriptFinder serviceGroovyScriptFinder;

    private GroovyClassLoader groovyClassLoader;

    @PostConstruct
    public void init() {
        this.groovyClassLoader = new DataGroovyClassLoader(Thread.currentThread().getContextClassLoader(), serviceGroovyScriptFinder);
    }

    public Object executeService(String classname, String method, Map<String, Object> param) {
        try {
            Class<?> serviceClass = this.groovyClassLoader.loadClass(classname);
            Object service = serviceClass.getDeclaredConstructor().newInstance();
            Method serviceMethod = serviceClass.getDeclaredMethod(method);
            return serviceMethod.invoke(service);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
