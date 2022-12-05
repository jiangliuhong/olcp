package top.jiangliuhong.olcp.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.jiangliuhong.olcp.common.bean.ApiResult;
import top.jiangliuhong.olcp.common.consts.ApiResultStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 */
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ApiResult<Void> restResult = ApiResult.fail(ApiResultStatus.UNAUTHORIZED);
        response.setStatus(restResult.getCode());
        out.write(new ObjectMapper().writeValueAsString(restResult));
        out.flush();
        out.close();
    }
}
