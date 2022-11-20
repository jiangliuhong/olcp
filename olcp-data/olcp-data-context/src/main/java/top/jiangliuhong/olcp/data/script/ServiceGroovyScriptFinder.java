package top.jiangliuhong.olcp.data.script;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.data.bean.po.GroovyFilePO;
import top.jiangliuhong.olcp.data.event.GroovyFileChangeEvent;
import top.jiangliuhong.olcp.data.service.GroovyFileService;

import java.util.HashSet;
import java.util.Set;

@Component
public class ServiceGroovyScriptFinder implements IGroovyScriptFinder, ApplicationListener<GroovyFileChangeEvent> {

    @Autowired
    private GroovyFileService groovyFileService;

    private final Set<String> needUpdate = new HashSet<>();

    @Override
    public void onApplicationEvent(GroovyFileChangeEvent event) {
        String key = generateKey(event.getAppName(), event.getFolder(), event.getName());
        this.needUpdate.add(key);
    }

    @Override
    public String findByClassname(String classname) {
        // olcp.test.User
        // olcp.test.u.User
        String[] split = classname.split("\\.");
        if (split.length < 2) {
            return null;
        }
        if (!StringUtils.equals(split[0], "olcp")) {
            return null;
        }
        String folder = this.resolveFolder(split);
        String appName = split[1];
        String name = split[split.length - 1];
        // TODO classname可能为groovy自动生成的类，需要过滤一下
        GroovyFilePO file = groovyFileService.get(appName, folder, name);
        if (file == null) {
            return null;
        }
        String script = file.getScript();
        if (StringUtils.isBlank(script)) {
            throw new RuntimeException("service class script is empty:" + classname);
        }
        String key = this.generateKey(appName, folder, name);
        this.needUpdate.remove(key);
        return file.getScript();
    }

    @Override
    public boolean isRecompilable(String classname) {
        String[] split = classname.split("\\.");
        if (split.length < 2) {
            return false;
        }
        if (!StringUtils.equals(split[0], "olcp")) {
            return false;
        }
        String folder = this.resolveFolder(split);
        String appName = split[1];
        String name = split[split.length - 1];
        String key = this.generateKey(appName, folder, name);
        return this.needUpdate.contains(key);
    }

    private String resolveFolder(String[] nameArray) {
        StringBuilder folderBuilder = new StringBuilder();
        if (nameArray.length > 3) {
            for (int i = 2; i < nameArray.length - 1; i++) {
                folderBuilder.append(nameArray[i]).append(".");
            }
            folderBuilder.delete(0, folderBuilder.length() - 1);
        }
        return folderBuilder.toString();
    }

    private String generateKey(String appName, String folder, String name) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(appName).append(".");
        if (StringUtils.isNotBlank(folder)) {
            keyBuilder.append(folder).append(".");
        }
        keyBuilder.append(name);
        return keyBuilder.toString();
    }
}
