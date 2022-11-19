package top.jiangliuhong.olcp.data.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.exception.BusinessException;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.GroovyFileDO;
import top.jiangliuhong.olcp.data.bean.po.GroovyFilePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.dao.GroovyFileRepository;
import top.jiangliuhong.olcp.data.exception.AppException;

import java.util.Optional;

@Service
public class GroovyFileService {

    @Autowired
    private GroovyFileRepository groovyFileRepository;

    public void save(GroovyFilePO file) {
        if (StringUtils.isBlank(file.getName())) {
            throw new BusinessException("文件名称不能为空");
        }
        if (StringUtils.isBlank(file.getScript())) {
            throw new BusinessException("文件内容不能为空");
        }
        int count = groovyFileRepository.countByAppIdAndFolderAndName(file.getAppId(), file.getFolder(), file.getName());
        if (count >= 1) {
            throw new BusinessException("文件" + file.getFolder() + "." + file.getName() + "已存在");
        }
        GroovyFileDO groovyFileDO = BeanUtils.copyBean(file, GroovyFileDO.class);
        if (!CacheUtils.exist(CacheNames.APP_ID, file.getAppId())) {
            throw new AppException("app id is wrong:" + file.getAppId());
        }
        groovyFileRepository.save(groovyFileDO);
        file.setId(groovyFileDO.getId());
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
        Optional<GroovyFileDO> optional = groovyFileRepository.findById(file.getId());
        if (optional.isEmpty()) {
            throw new BusinessException("请传入正确的ID");
        }
        GroovyFileDO groovyFileDO = optional.get();
        if (StringUtils.equals(groovyFileDO.getName(), file.getName())
                || StringUtils.equals(groovyFileDO.getFolder(), file.getFolder())) {
            int count = groovyFileRepository.countByAppIdAndFolderAndName(file.getAppId(), file.getFolder(), file.getName());
            if (count >= 1) {
                throw new BusinessException("文件" + file.getFolder() + "." + file.getName() + "已存在");
            }
        }
        BeanUtils.copyNotNullProperties(file, groovyFileDO, "appId");
        groovyFileDO.setName(file.getName());
        groovyFileDO.setFolder(file.getFolder());
        if (StringUtils.isNotBlank(file.getScript())) {
            groovyFileDO.setScript(file.getScript());
        }
        groovyFileRepository.save(groovyFileDO);
    }

    public GroovyFilePO get(String appId, String folder, String fileName) {
        GroovyFileDO file = groovyFileRepository.findByAppIdAndFolderAndName(appId, folder, fileName);
        return BeanUtils.copyBean(file, GroovyFilePO.class);
    }

}