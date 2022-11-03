package top.jiangliuhong.olcp.data.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.AppCreateVO;
import top.jiangliuhong.olcp.data.bean.AppUpdateVO;
import top.jiangliuhong.olcp.data.bean.AppVO;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.service.AppService;

@Tag(name = "app", description = "应用程序API")
@RestController
@RequestMapping("/api/v1/data/app")
public class AppApi {

    @Autowired
    private AppService appService;

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取应用")
    public AppVO getCurrentApp(@PathVariable String id) {
        AppPO appPO = appService.getApp(id);
        if (appPO == null) {
            return null;
        }
        AppVO app = new AppVO();
        BeanUtils.copyProperties(appPO, app);
        return app;
    }

    @PostMapping
    @Operation(summary = "新增应用程序")
    public String add(@RequestBody AppCreateVO appVO) {
        AppDO app = new AppDO();
        app.setName(appVO.getName());
        app.setTitle(appVO.getTitle());
        appService.addApp(app);
        return app.getId();
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改应用程序")
    public void update(@PathVariable String id, @RequestBody AppUpdateVO appVO) {
        AppDO app = new AppDO();
        app.setId(id);
        app.setTitle(appVO.getTitle());
        appService.update(app);
    }
}
