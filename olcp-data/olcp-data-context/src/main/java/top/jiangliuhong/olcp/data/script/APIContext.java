package top.jiangliuhong.olcp.data.script;

import lombok.Getter;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
public class APIContext {

    private final HttpMethod httpMethod;
    private final String path;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public APIContext(String path, HttpMethod httpMethod, HttpServletRequest request, HttpServletResponse response) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.request = request;
        this.response = response;
    }

    public String getHttpMethodName() {
        return this.httpMethod.name();
    }
}
