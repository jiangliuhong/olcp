package top.jiangliuhong.olcp.data.script;

public interface IGroovyScriptFinder {

    public String findByClassname(String classname);

    public boolean isRecompilable(String classname);

    public boolean isPackageName(String classname);

}
