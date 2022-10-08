package top.jiangliuhong.olcp.test.engine;

import org.junit.jupiter.api.Test;
import top.jiangliuhong.olcp.engine.load.AppLoader;

import static com.alibaba.testable.core.matcher.InvocationVerifier.verifyInvoked;

public class AppLoaderTests {

    public static class Mock {

    }

    private final AppLoader appLoader = new AppLoader();

    @Test
    public void TestLoadSysApp() throws Exception {
        String sysAppPath = "classpath:top/jiangliuhong/olcp/app/sys";
        appLoader.load(sysAppPath);
        //verifyInvoked("loadAppSource").with(sysAppPath).withTimes(1);
    }

}
