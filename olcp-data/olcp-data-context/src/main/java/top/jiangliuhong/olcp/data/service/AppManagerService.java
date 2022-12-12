package top.jiangliuhong.olcp.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.cache.ICache;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.consts.CacheNames;

import java.util.ArrayList;
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
    public List<AppPO> getUserAppList(String userId) {
        //TODO 根据用户过滤
        List<AppPO> apps = new ArrayList<>();
        ICache<String, AppPO> appCache = CacheUtils.getCache(CacheNames.APP_ID);
        appCache.entrySet().forEach(entry -> apps.add(entry.getValue()));
        return apps;
    }
}
