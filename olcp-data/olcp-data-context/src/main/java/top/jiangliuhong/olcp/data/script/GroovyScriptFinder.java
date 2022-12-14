package top.jiangliuhong.olcp.data.script;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.event.GroovyFileChangeEvent;
import top.jiangliuhong.olcp.data.exception.GroovyScriptNotFoundException;

import java.util.HashSet;
import java.util.Set;

@Component
public class GroovyScriptFinder implements IGroovyScriptFinder, ApplicationListener<GroovyFileChangeEvent> {

    private final Set<String> needUpdate = new HashSet<>();

    @Override
    public void onApplicationEvent(GroovyFileChangeEvent event) {
        GroovyFileChangeEvent.GroovyFileChange message = event.getMessage();
        String key = message.getFolder() + "." + message.getName();
        this.needUpdate.add(key);
    }

    @Override
    public String findByClassname(String classname) {
        if (!this.isPackageName(classname)) {
            return null;
        }
        String script = CacheUtils.getCacheValue(CacheNames.GROOVY_FILE_SCRIPT, classname);
        if (StringUtils.isBlank(script)) {
            throw new GroovyScriptNotFoundException("class script is empty:" + classname);
        }
        this.needUpdate.remove(classname);
        return script;
    }

    @Override
    public boolean isRecompilable(String classname) {
        return this.isPackageName(classname) && this.needUpdate.contains(classname);
    }

    @Override
    public boolean isPackageName(String classname) {
        Set<String> keys = CacheUtils.keys(CacheNames.GROOVY_FILE_SCRIPT);
        return keys.contains(classname);
    }

}
