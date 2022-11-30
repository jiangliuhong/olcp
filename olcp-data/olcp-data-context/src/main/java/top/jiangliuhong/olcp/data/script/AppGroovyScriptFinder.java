package top.jiangliuhong.olcp.data.script;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.exception.GroovyScriptNotFoundException;

public class AppGroovyScriptFinder implements IGroovyScriptFinder {

    private final String appName;
    private final GroovyScriptFinder finder;

    public AppGroovyScriptFinder(String appName, GroovyScriptFinder finder) {
        this.appName = appName;
        this.finder = finder;
    }

    @Override
    public String findByClassname(String classname) {
        // 判断是否属于此应用
        if (!this.isPackageName(classname)) {
            return null;
        }
        String appName = CacheUtils.getCacheValue(CacheNames.GROOVY_FILE_APP, classname);
        if (!StringUtils.equals(appName, this.appName)) {
            throw new GroovyScriptNotFoundException(classname + "属于" + appName + ",不能在中" + this.appName + "引用");
        }
        return this.finder.findByClassname(classname);
    }

    @Override
    public boolean isRecompilable(String classname) {
        return this.finder.isRecompilable(classname);
    }

    @Override
    public boolean isPackageName(String classname) {
        return this.finder.isPackageName(classname);
    }
}
