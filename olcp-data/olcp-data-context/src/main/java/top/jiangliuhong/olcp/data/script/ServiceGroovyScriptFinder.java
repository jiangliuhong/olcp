package top.jiangliuhong.olcp.data.script;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.data.bean.po.GroovyFilePO;
import top.jiangliuhong.olcp.data.service.GroovyFileService;
import top.jiangliuhong.olcp.data.service.ServiceService;

@Component
public class ServiceGroovyScriptFinder implements IGroovyScriptFinder {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private GroovyFileService groovyFileService;

    @Override
    public String findByClassname(String classname) {
        String[] split = classname.split("\\.");
        if (split.length != 3) {
            return null;
        }
        String basePackage = split[0];
        if (!StringUtils.equals(basePackage, "olcp")) {
            return null;
        }
        String appName = split[1];
        String serviceName = split[2];
        GroovyFilePO service = serviceService.getService(appName, serviceName);
        if (service == null) {
            throw new RuntimeException("not found service class :" + classname);
        }
        String script = service.getScript();
        if (StringUtils.isBlank(script)) {
            throw new RuntimeException("service class script is empty:" + classname);
        }
        return service.getScript();
    }

    @Override
    public boolean isRecompilable(String classname) {
        String[] split = classname.split("\\.");
        if (split.length != 3) {
            return false;
        }
        String basePackage = split[0];
        if (!StringUtils.equals(basePackage, "olcp")) {
            return false;
        }
        // TODO 判断是否需要更新脚本
        return true;
    }

}
