package top.jiangliuhong.olcp.data.config;

import groovy.lang.GroovyClassLoader;

public class DataGroovyClassLoader extends GroovyClassLoader {

    public DataGroovyClassLoader(ClassLoader loader) {
        super(loader);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println(1111);
        System.out.println(name);
        return super.findClass(name);
    }
}
