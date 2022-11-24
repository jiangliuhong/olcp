package top.jiangliuhong.olcp.data.script.function;

import top.jiangliuhong.olcp.data.script.APIContext;

public abstract class AbstractApi {

    private APIContext context;

    public void setContext(APIContext context) {
        this.context = context;
    }
}
