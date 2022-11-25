package top.jiangliuhong.olcp.sdk.context;

import top.jiangliuhong.olcp.sdk.bean.User;

public interface APIContext {

    public String getParameter(String name);

    public byte[] getRequestBody();

    public String getPath();

    public String getMethod();

    public String getCurrentUserId();

    public User getCurrentUser();
}
