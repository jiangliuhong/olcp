package top.jiangliuhong.olcp.data.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import top.jiangliuhong.olcp.data.script.APIContext;
import top.jiangliuhong.olcp.data.script.ScriptExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "execute", description = "执行脚本API")
@RestController
@RequestMapping("/api/service")
public class ExecuteApi {

    @Autowired
    private ScriptExecution executionService;

    @PostMapping("/{*path}")
    public Object executePostService(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        APIContext context = new APIContext(path, HttpMethod.POST, request, response);
        return executionService.runAPI(context);
    }

    @GetMapping("/{*path}")
    public Object executeGetService(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        APIContext context = new APIContext(path, HttpMethod.GET, request, response);
        return executionService.runAPI(context);
    }

    @PutMapping("/{*path}")
    public Object executePutService(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        APIContext context = new APIContext(path, HttpMethod.PUT, request, response);
        return executionService.runAPI(context);
    }

    @DeleteMapping("/{*path}")
    public Object executeDeleteService(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        APIContext context = new APIContext(path, HttpMethod.DELETE, request, response);
        return executionService.runAPI(context);
    }

}
