package top.jiangliuhong.olcp.data.script;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.utils.ExceptionUtils;
import top.jiangliuhong.olcp.data.annotation.*;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.context.AppContext;
import top.jiangliuhong.olcp.data.context.AppContextHolder;
import top.jiangliuhong.olcp.data.function.AbstractApi;
import top.jiangliuhong.olcp.data.script.context.HttpAPIContext;
import top.jiangliuhong.olcp.data.script.exception.APIExecuteException;
import top.jiangliuhong.olcp.data.script.exception.APINotFoundException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Service
public class ScriptExecution {

    private final Map<String, String> API_MAP = new ConcurrentHashMap<>();

    public Object runAPI(HttpAPIContext context) {
        if (StringUtils.isBlank(context.getPath())) {
            throw new APINotFoundException("api path is empty");
        }
        String key = context.getHttpMethodName() + "." + context.getPath();
        if (!this.API_MAP.containsKey(key)) {
            throw new APINotFoundException("not found api path:" + context.getPath());
        }
        String apiValue = this.API_MAP.get(key);
        int index = apiValue.lastIndexOf(".");
        String classname = apiValue.substring(0, index);
        String method = apiValue.substring(index + 1);
        try {
            Class<?> clazz = ScriptRunner.loadClass(classname);
            String appName = CacheUtils.getCacheValue(CacheNames.GROOVY_FILE_APP, classname);
            this.preHandler(appName);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            if (instance instanceof AbstractApi) {
                ((AbstractApi) instance).setContext(context);
            }
            Method declaredMethod = clazz.getDeclaredMethod(method);
            Object result = declaredMethod.invoke(instance);
            this.postHandler();
            return result;
        } catch (Exception e) {
            String message = ExceptionUtils.getExceptionMessage(e);
            throw new APIExecuteException("执行API异常," + message, e);
        }
    }

    public void loadAPI(String appName, Class<?> clazz) {
        Api api = clazz.getAnnotation(Api.class);
        if (api == null) {
            return;
        }
        String baseUrl = api.value();
        String classname = clazz.getName();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getParameterCount() != 0) {
                continue;
            }
            String value = classname + "." + method.getName();
            Post post = method.getAnnotation(Post.class);
            if (post != null) {
                this.putAPI(appName, HttpMethod.POST, baseUrl, post.value(), value);
                continue;
            }
            Put put = method.getAnnotation(Put.class);
            if (put != null) {
                this.putAPI(appName, HttpMethod.PUT, baseUrl, put.value(), value);
                continue;
            }
            Get get = method.getAnnotation(Get.class);
            if (get != null) {
                this.putAPI(appName, HttpMethod.GET, baseUrl, get.value(), value);
                continue;
            }
            Delete delete = method.getAnnotation(Delete.class);
            if (delete != null) {
                this.putAPI(appName, HttpMethod.DELETE, baseUrl, delete.value(), value);
            }
        }
    }

    private void putAPI(String appName, HttpMethod type, String base, String url, String value) {
        url = base + url;
        if (StringUtils.startsWith(url, "/")) {
            url = "/" + appName + url;
        } else {
            url = "/" + appName + "/" + url;
        }
        String key = type.name() + "." + url;
        if (this.API_MAP.containsKey(key)) {
            log.error("Duplicate URL :" + key + " for " + value);
        } else {
            this.API_MAP.put(key, value);
        }
    }

    private void preHandler(String appName) {
//        Thread.currentThread().
        AppContext context = new AppContext(appName);
        AppContextHolder.setCurrentAppContext(context);
    }

    private void postHandler() {
        AppContextHolder.removeCurrentDataAppContext();
    }


}
