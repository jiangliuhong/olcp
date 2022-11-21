package top.jiangliuhong.olcp.data.script;

import groovy.lang.GroovyClassLoader;

public class DataGroovyClassLoader extends GroovyClassLoader {

    private final IGroovyScriptFinder finder;

    public DataGroovyClassLoader(ClassLoader loader, IGroovyScriptFinder finder) {
        super(loader);
        this.finder = finder;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
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
}
