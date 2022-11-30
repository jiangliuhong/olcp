package top.jiangliuhong.olcp.data.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.lang.Nullable;

/**
 * data app context holder
 */
public final class AppContextHolder {

    private static final ThreadLocal<AppContext> currentAppContext = new NamedInheritableThreadLocal<>("Data App Context");

    private AppContextHolder() {
    }

    public static void setCurrentAppContext(AppContext appContext) {
        currentAppContext.set(appContext);
    }

    @Nullable
    public static AppContext getCurrentAppContext() {
        return currentAppContext.get();
    }

    @Nullable
    public static AppContext removeCurrentDataAppContext() {
        AppContext appContext = currentAppContext.get();
        currentAppContext.remove();
        return appContext;
    }
}
