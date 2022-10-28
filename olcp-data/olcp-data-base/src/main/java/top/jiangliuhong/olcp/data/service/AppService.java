package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
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

@Service
public class AppService {

    @Resource
    private AppRepository appRepository;

    public AppCachePO getApp(String id) {
        return CacheUtils.getCacheValue(CacheNames.APP, id);
    }

    @Transactional
    public void addApp(AppDO app) {
        if (StringUtils.isBlank(app.getName())) {
            throw new AppException("请传入应用名称");
        }
        if (CacheUtils.exist(CacheNames.APP, app.getName())) {
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
        if (!CacheUtils.exist(CacheNames.APP, app.getId())) {
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

    private void saveCache(AppDO app) {
        AppCachePO appCachePO = new AppCachePO();
        BeanUtils.copyProperties(app, appCachePO);
        CacheUtils.putCacheValue(CacheNames.APP, app.getId(), appCachePO);
        CacheUtils.putCacheValue(CacheNames.APP, app.getName(), appCachePO);
    }
}
