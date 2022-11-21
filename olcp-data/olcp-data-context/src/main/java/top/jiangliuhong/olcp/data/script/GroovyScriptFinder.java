package top.jiangliuhong.olcp.data.script;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.data.bean.po.GroovyFilePO;
import top.jiangliuhong.olcp.data.event.GroovyFileChangeEvent;
import top.jiangliuhong.olcp.data.service.AppService;
import top.jiangliuhong.olcp.data.service.GroovyFileService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class GroovyScriptFinder implements IGroovyScriptFinder, ApplicationListener<GroovyFileChangeEvent> {

    @Autowired
    private GroovyFileService groovyFileService;
    @Autowired
    private AppService appService;

    private final Set<String> needUpdate = new HashSet<>();
    private final Set<String> packageNames = new HashSet<>();

    @PostConstruct
    public void init() {
        String[] serverAppIds = appService.getServerAppIds();
        Map<String, List<String>> fileNameMaps = groovyFileService.getFileName(serverAppIds);
        fileNameMaps.forEach((appName, fileNames) -> {
            packageNames.addAll(fileNames);
        });
    }

    @Override
    public void onApplicationEvent(GroovyFileChangeEvent event) {
        GroovyFileChangeEvent.GroovyFileChange message = event.getMessage();
        String oldKey = message.getOldFolder() + "." + message.getOldName();
        this.packageNames.remove(oldKey);
        String key = message.getFolder() + "." + message.getName();
        this.needUpdate.add(key);
        this.packageNames.add(key);
    }

    @Override
    public String findByClassname(String classname) {
        if (this.packageNames.contains(classname)) {
            return null;
        }
        String[] split = classname.split("\\.");
        String folder = this.resolveFolder(split);
        String appName = split[0];
        String name = split[split.length - 1];
        GroovyFilePO file = groovyFileService.get(appName, folder, name);
        if (file == null) {
            return null;
        }
        String script = file.getScript();
        if (StringUtils.isBlank(script)) {
            throw new RuntimeException("service class script is empty:" + classname);
        }
        this.needUpdate.remove(classname);
        return file.getScript();
    }

    @Override
    public boolean isRecompilable(String classname) {
        return this.packageNames.contains(classname) && this.needUpdate.contains(classname);
    }

    private String resolveFolder(String[] nameArray) {
        StringBuilder folderBuilder = new StringBuilder();
        for (int i = 0; i < nameArray.length - 1; i++) {
            folderBuilder.append(nameArray[i]).append(".");
        }
        folderBuilder.delete(0, folderBuilder.length() - 1);
        return folderBuilder.toString();
    }

}
