package top.jiangliuhong.olcp.data.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jiangliuhong.olcp.data.bean.ServiceExecuteVO;
import top.jiangliuhong.olcp.data.service.ScriptExecutionService;

@Tag(name = "service", description = "应用服务API")
@RestController
@RequestMapping("/api/v1/data/service")
public class ServiceApi {

    @Autowired
    private ScriptExecutionService scriptExecutionService;

    @PostMapping
    public Object executeService(@RequestBody ServiceExecuteVO serviceExecuteVO) {
        Object res = scriptExecutionService.executeService(
                serviceExecuteVO.getClassname(),
                serviceExecuteVO.getMethod(),
                serviceExecuteVO.getParameters());
        return res;
    }
}
