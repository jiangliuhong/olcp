package top.jiangliuhong.olcp.sdk.function;

import com.google.gson.Gson;
import top.jiangliuhong.olcp.sdk.context.APIContext;

import java.util.Map;

public abstract class AbstractApi {

    protected APIContext context;

    public void setContext(APIContext context) {
        this.context = context;
    }

    protected String getParameter(String name) {
        return this.context.getParameter(name);
    }

    protected String getBodyString() {
        return new String(this.getRequestBody());
    }

    protected Map<String, Object> getBodyMap() {
        return this.getBody(Map.class);
    }

    protected <T> T getBody(Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(this.getBodyString(), clazz);
    }

    private byte[] getRequestBody() {
        return this.context.getRequestBody();
    }
}
