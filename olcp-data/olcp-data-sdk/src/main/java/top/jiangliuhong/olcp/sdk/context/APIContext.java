package top.jiangliuhong.olcp.sdk.context;

public interface APIContext {

    public String getParameter(String name);

    public byte[] getRequestBody();

    public String getPath();

    public String getMethod();
}
