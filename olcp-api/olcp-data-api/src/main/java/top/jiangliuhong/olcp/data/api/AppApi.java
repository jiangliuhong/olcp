package top.jiangliuhong.olcp.data.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.AppVO;
import top.jiangliuhong.olcp.data.service.AppService;

@Tag(name = "app", description = "应用程序API")
@RestController
@RequestMapping("/api/v1/data/app")
public class AppApi {

    @Autowired
    private AppService appService;

    @PostMapping
    @Operation(summary = "新增应用程序")
    public String add(@RequestBody AppVO appVO) {
        AppDO app = new AppDO();
        app.setName(appVO.getName());
        app.setTitle(appVO.getTitle());
        appService.addApp(app);
        return app.getId();
    }
}
