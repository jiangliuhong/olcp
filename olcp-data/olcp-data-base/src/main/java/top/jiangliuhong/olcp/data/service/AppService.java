package top.jiangliuhong.olcp.data.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.dao.AppRepository;
import top.jiangliuhong.olcp.data.exception.AppException;

import javax.annotation.Resource;

@Service
public class AppService {

    @Resource
    private AppRepository appRepository;

    public void addApp(AppDO app) {
        if (StringUtils.isBlank(app.getName())) {
            throw new AppException("请传入应用名称");
        }
        if (StringUtils.isBlank(app.getTitle())) {
            app.setTitle(app.getName());
        }
        appRepository.save(app);
    }

    public void update(AppDO app) {
        if (StringUtils.isBlank(app.getId())) {
            throw new AppException("请传入应用ID");
        }
        if (StringUtils.isBlank(app.getName())) {
            throw new AppException("请传入应用名称");
        }
        if (StringUtils.isBlank(app.getTitle())) {
            app.setTitle(app.getName());
        }
        // TODO use cache
        if (appRepository.existsById(app.getId())) {
            throw new AppException("请传入正确的应用ID");
        }
        appRepository.save(app);
    }
}
