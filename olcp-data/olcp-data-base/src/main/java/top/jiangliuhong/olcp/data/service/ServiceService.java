package top.jiangliuhong.olcp.data.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.bean.po.ServicePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;

@Log4j2
@Service
public class ServiceService {

    public void saveCache(ServicePO service) {
        AppPO app = CacheUtils.getCacheValue(CacheNames.SERVICE_ID, service.getAppId());
        if (app == null) {
            log.error("service cache error:table[" + service.getName() + "] appId[" + service.getAppId() + "] is not exist");
            return;
        }
        CacheUtils.putCacheValue(CacheNames.SERVICE_ID, service.getId(), service);
    }
}
