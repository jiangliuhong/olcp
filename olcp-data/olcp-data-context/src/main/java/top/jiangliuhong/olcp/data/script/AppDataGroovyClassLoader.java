package top.jiangliuhong.olcp.data.script;

public class AppDataGroovyClassLoader extends DataGroovyClassLoader {

    private final String name;

    public AppDataGroovyClassLoader(ClassLoader loader, IGroovyScriptFinder finder, String name) {
        super(loader, finder);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
