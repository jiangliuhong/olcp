package top.jiangliuhong.olcp.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.data.bean.AppDO;

import java.util.List;

/**
 * 应用管理服务
 */
@Service
public class AppManagerService {

    @Autowired
    private AppService appService;

    /**
     * 根据用户查询应用
     *
     * @param userId 用户ID
     * @return 应用列表
     */
    public List<AppDO> getUserAppList(String userId) {
        //TODO 根据用户过滤
        return appService.getAllApp();
    }
}
