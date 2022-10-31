package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.cache.AppCachePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.dao.AppRepository;
import top.jiangliuhong.olcp.data.exception.AppException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppService {

    @Resource
    private AppRepository appRepository;
    @Value("${olcp.app-id}")
    private String[] appIds;

    public String[] getServerAppIds() {
        if (ArrayUtils.contains(this.appIds, "all") || ArrayUtils.contains(this.appIds, "ALL")) {
//            ICache<Object, Object> cache = CacheUtils.getCache(CacheNames.APP);
            Set<String> keys = (Set<String>)CacheUtils.keys(CacheNames.APP_ID);
            return ArrayUtils.toArray();
        }
        return appIds;
    }

    public AppCachePO getApp(String id) {
        return CacheUtils.getCacheValue(CacheNames.APP_ID, id);
    }

    @Transactional
    public void addApp(AppDO app) {
        if (StringUtils.isBlank(app.getName())) {
            throw new AppException("请传入应用名称");
        }
        if (CacheUtils.exist(CacheNames.APP_NAME, app.getName())) {
            throw new AppException("应用已存在");
        }
        if (StringUtils.isBlank(app.getTitle())) {
            app.setTitle(app.getName());
        }
        this.appRepository.save(app);
        this.saveCache(app);
    }

    @Transactional
    public void update(AppDO app) {
        if (StringUtils.isBlank(app.getId())) {
            throw new AppException("请传入应用ID");
        }
        if (!CacheUtils.exist(CacheNames.APP_ID, app.getId())) {
            throw new AppException("请传入正确的应用ID");
        }
        Optional<AppDO> appDOOptional = this.appRepository.findById(app.getId());
        AppDO appByDB = appDOOptional.orElseThrow(() -> new AppException("not found app by " + app.getId()));
        BeanUtils.copyNotNullProperties(app, appByDB);
        this.appRepository.save(appByDB);
        this.saveCache(appByDB);
    }

    public List<AppDO> getAllApp() {
        Iterable<AppDO> all = this.appRepository.findAll();
        return IteratorUtils.toList(all.iterator());
    }

    /**
     * 保存或更新应用缓存
     *
     * @param app app info
     */
    public void saveCache(AppDO app) {
        AppCachePO appCachePO = new AppCachePO();
        BeanUtils.copyProperties(app, appCachePO);
        CacheUtils.putCacheValue(CacheNames.APP_ID, app.getId(), appCachePO);
        CacheUtils.putCacheValue(CacheNames.APP_NAME, app.getName(), app.getId());
    }

}
