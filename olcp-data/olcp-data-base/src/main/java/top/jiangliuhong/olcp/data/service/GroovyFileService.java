package top.jiangliuhong.olcp.data.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.exception.BusinessException;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.GroovyFileDO;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.bean.po.GroovyFilePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.dao.GroovyFileRepository;
import top.jiangliuhong.olcp.data.event.GroovyFileChangeEvent;
import top.jiangliuhong.olcp.data.exception.AppException;

import java.util.*;

@Service
public class GroovyFileService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private GroovyFileRepository groovyFileRepository;

    public void save(GroovyFilePO file) {
        if (StringUtils.isBlank(file.getName())) {
            throw new BusinessException("文件名称不能为空");
        }
        if (StringUtils.isBlank(file.getScript())) {
            throw new BusinessException("文件内容不能为空");
        }
        if (StringUtils.isBlank(file.getFolder())) {
            throw new BusinessException("文件目录不能为空");
        }
        checkFileExist(file);
        AppPO app = CacheUtils.getCacheValue(CacheNames.APP_ID, file.getAppId());
        if (app == null) {
            throw new AppException("app id is wrong:" + file.getAppId());
        }
//        if (!file.getFolder().startsWith(app.getName() + ".")) {
//            file.setFolder(app.getName() + "." + file.getFolder());
//        }
        GroovyFileDO groovyFileDO = BeanUtils.copyBean(file, GroovyFileDO.class);
        groovyFileRepository.save(groovyFileDO);
        file.setId(groovyFileDO.getId());
        this.saveCache(file);
    }

    public void update(GroovyFilePO file) {
        if (StringUtils.isBlank(file.getId())) {
            throw new BusinessException("ID不能为空");
        }
        if (StringUtils.isBlank(file.getName())) {
            throw new BusinessException("文件名称不能为空");
        }
        if (StringUtils.isBlank(file.getScript())) {
            throw new BusinessException("文件内容不能为空");
        }
        if (StringUtils.isBlank(file.getFolder())) {
            throw new BusinessException("文件目录不能为空");
        }
        Optional<GroovyFileDO> optional = groovyFileRepository.findById(file.getId());
        if (optional.isEmpty()) {
            throw new BusinessException("请传入正确的ID");
        }
        GroovyFileDO groovyFileDO = optional.get();
        boolean nameChange = !StringUtils.equals(groovyFileDO.getName(), file.getName())
                || !StringUtils.equals(groovyFileDO.getFolder(), file.getFolder());
        if (nameChange) {
            checkFileExist(file);
        }
        String oldFolder = groovyFileDO.getFolder();
        String oldName = groovyFileDO.getName();
        BeanUtils.copyNotNullProperties(file, groovyFileDO, "appId");
        groovyFileDO.setName(file.getName());
        groovyFileDO.setFolder(file.getFolder());
        if (StringUtils.isNotBlank(file.getScript())) {
            groovyFileDO.setScript(file.getScript());
        }
        groovyFileRepository.save(groovyFileDO);
        AppPO appPO = CacheUtils.getCacheValue(CacheNames.APP_ID, groovyFileDO.getAppId());
        GroovyFileChangeEvent event = GroovyFileChangeEvent.builder()
                .source(this)
                .appName(appPO.getName())
                .file(groovyFileDO.getFolder(), groovyFileDO.getName())
                .oldFile(oldFolder, oldName)
                .build();
        if (nameChange) {
            this.deleteCache(oldFolder, oldName);
        }
        this.saveCache(file);
        this.applicationEventPublisher.publishEvent(event);
    }

    public GroovyFilePO get(String appName, String folder, String name) {
        String appId = CacheUtils.getCacheValue(CacheNames.APP_NAME, appName);
        if (StringUtils.isBlank(appId)) {
            throw new BusinessException("appName is wrong:" + appName);
        }
        GroovyFileDO file = groovyFileRepository.findByAppIdAndFolderAndName(appId, folder, name);
        return BeanUtils.copyBean(file, GroovyFilePO.class);
    }

    public List<GroovyFilePO> getFiles(String... appIds) {
        List<GroovyFileDO> files = groovyFileRepository.findAllByAppIdIn(appIds);
        return BeanUtils.copyBean(files, GroovyFilePO.class);
    }

    public void saveCache(GroovyFilePO file) {
        String fileName = file.getFolder() + "." + file.getName();
        CacheUtils.putCacheValue(CacheNames.GROOVY_FILE, fileName, file.getScript());
    }

    public void deleteCache(String folder, String name) {
        String fileName = folder + "." + name;
        CacheUtils.remove(CacheNames.GROOVY_FILE, fileName);
    }


    public Map<String, List<String>> getFileName(String... appIds) {
        Map<String, List<String>> res = new HashMap<>();
        if (appIds.length == 0) {
            return res;
        }
        List<GroovyFileDO> files = groovyFileRepository.findFolderNameByAppId(appIds);
        if (files != null && files.size() > 0) {
            files.forEach(file -> {
                AppPO app = CacheUtils.getCacheValue(CacheNames.APP_ID, file.getAppId());
                String appName = app.getName();
                if (!res.containsKey(appName)) {
                    res.put(appName, new ArrayList<>());
                }
                List<String> strings = res.get(appName);
                String fileName = (StringUtils.isBlank(file.getFolder()) ? "" : file.getFolder() + ".") + file.getName();
                strings.add(fileName);
            });
        }
        return res;
    }

    private void checkFileExist(GroovyFilePO file) {
        int count = groovyFileRepository.countByAppIdAndFolderAndName(file.getAppId(), file.getFolder(), file.getName());
        if (count >= 1) {
            String folder = StringUtils.isBlank(file.getFolder()) ? "" : file.getFolder() + ".";
            throw new BusinessException("文件" + folder + file.getName() + "已存在");
        }
    }
}
