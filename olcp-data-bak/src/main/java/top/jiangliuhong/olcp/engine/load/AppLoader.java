package top.jiangliuhong.olcp.engine.load;

import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.engine.load.bean.AppSource;

import javax.annotation.Resource;

/**
 * load application
 */
@Component
public class AppLoader {

    private final AppSourceLoader sourceLoader = new AppSourceLoader();
    @Resource
    private AppRunDatabase appRunDatabase;

    public void load(String path) throws Exception {
        // load source
        AppSource appSource = this.loadAppSource(path);
        // apply database
        appRunDatabase.run(appSource);
    }

    private AppSource loadAppSource(String path) throws Exception {
        return sourceLoader.load(path);
    }

}
