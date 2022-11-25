package top.jiangliuhong.olcp.data.script.context;

import org.springframework.http.HttpMethod;
import top.jiangliuhong.olcp.data.script.exception.APIExecuteException;
import top.jiangliuhong.olcp.sdk.context.APIContext;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpAPIContext implements APIContext {

    private final HttpMethod httpMethod;
    private final String path;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public HttpAPIContext(String path, HttpMethod httpMethod, HttpServletRequest request, HttpServletResponse response) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.request = request;
        this.response = response;
    }

    public String getHttpMethodName() {
        return this.httpMethod.name();
    }

    @Override
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }

    @Override
    public byte[] getRequestBody() {
        HttpServletRequest request = this.request;
        try (ServletInputStream stream = request.getInputStream()) {
            int len = request.getContentLength();
            byte[] buffer = new byte[len];
            stream.read(buffer, 0, len);
            return buffer;
        } catch (Exception e) {
            throw new APIExecuteException("get request body error", e);
        }
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getMethod() {
        return this.httpMethod.name();
    }
}
