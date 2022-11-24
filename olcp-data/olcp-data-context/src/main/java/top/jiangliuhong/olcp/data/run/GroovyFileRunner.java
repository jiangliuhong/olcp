package top.jiangliuhong.olcp.data.run;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import top.jiangliuhong.olcp.data.script.ScriptRunner;
import top.jiangliuhong.olcp.data.service.AppService;
import top.jiangliuhong.olcp.data.service.GroovyFileService;
import top.jiangliuhong.olcp.data.script.ScriptExecution;

import java.util.List;
import java.util.Map;

@Log4j2
@Order(30)
public class GroovyFileRunner implements CommandLineRunner {

    @Autowired
    private AppService appService;
    @Autowired
    private GroovyFileService groovyFileService;
    @Autowired
    private ScriptExecution executionService;

    @Override
    public void run(String... args) throws Exception {
        // 加载所有groovy file
        String[] serverAppIds = appService.getServerAppIds();
        Map<String, List<String>> fileNameMap = groovyFileService.getFileName(serverAppIds);
        fileNameMap.forEach((appName, names) -> names.forEach(name -> this.loadClass(appName, name)));
    }

    /**
     * 提前加载所有类，并提取Api 、 Ws类
     *
     * @param classname classname
     */
    private void loadClass(String appName, String classname) {
        try {
            Class<?> clazz = ScriptRunner.getClassLoader().loadClass(classname);
            executionService.loadAPI(appName,clazz);
        } catch (ClassNotFoundException e) {
            log.error("load class error,class[" + classname + "] not found", e);
        }
    }
}
