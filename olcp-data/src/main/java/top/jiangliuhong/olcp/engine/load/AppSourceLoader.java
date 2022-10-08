package top.jiangliuhong.olcp.engine.load;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import top.jiangliuhong.olcp.engine.load.bean.AppConfig;
import top.jiangliuhong.olcp.engine.load.bean.AppSource;
import top.jiangliuhong.olcp.engine.load.bean.EntityInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * load app source file
 */
public class AppSourceLoader {

    public AppSource load(String appBasePath) throws Exception {
        if (appBasePath.startsWith(FileConstants.PREFIX_CLASSPATH)) {
            // load project src app
            String resourcePath = appBasePath.substring(FileConstants.PREFIX_CLASSPATH.length());
            ClassPathResource cpr = new ClassPathResource(resourcePath);
            File file = cpr.getFile();
            if (!file.isDirectory()) {
                return null;
            }
            // filters valid folder
            File[] files = file.listFiles((f -> {
                return (StringUtils.containsAny(f.getName(), FileConstants.CATALOG_NAMES) && f.isDirectory()) ||
                        (StringUtils.containsAny(f.getName(), FileConstants.FILE_NAMES) && f.isFile());
            }));
            if (files == null || files.length == 0) {
                return null;
            }
            Map<String, File> level1Files = new HashMap<>();
            for (File level1File : files) {
                level1Files.put(level1File.getName(), level1File);
            }
            // load app config
            if (!level1Files.containsKey(FileConstants.NAME_APP_CONFIG)) {
                throw new RuntimeException("not found " + FileConstants.NAME_APP_CONFIG);
            }
            AppSource appSource = new AppSource();
            AppConfig appConfig = new AppYamlFileLoader(level1Files.get(FileConstants.NAME_APP_CONFIG)).load();
            FolderAllReader folderAllReader = new FolderAllReader();
            // load entity
            List<EntityInfo> entities = new ArrayList<>();
            if (level1Files.containsKey(FileConstants.CATALOG_ENTITY)) {
                folderAllReader.read(level1Files.get(FileConstants.CATALOG_ENTITY), entityFile -> {
                    EntityInfo entityInfo = new EntityXmlFileLoader(entityFile).load();
                    if (entityInfo != null) {
                        entities.add(entityInfo);
                    }
                });
            }
            appSource.setConfig(appConfig);
            appSource.setEntities(entities);
            return appSource;
        } else {
            // load file system app
            return null;
        }

    }
}
