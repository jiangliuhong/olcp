package top.jiangliuhong.olcp.data.context;

import top.jiangliuhong.olcp.data.bean.User;

public interface APIContext {

    public String getParameter(String name);

    public byte[] getRequestBody();

    public String getPath();

    public String getMethod();

    public String getCurrentUserId();

    public User getCurrentUser();
}
