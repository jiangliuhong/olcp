package top.jiangliuhong.olcp.engine.load;

import org.yaml.snakeyaml.Yaml;
import top.jiangliuhong.olcp.engine.load.bean.AppConfig;

import java.io.File;
import java.io.FileInputStream;

public class AppYamlFileLoader implements ILoader<AppConfig> {

    private final File file;

    public AppYamlFileLoader(File file) {
        if (file == null) {
            throw new RuntimeException("the file must not be null");
        }
        this.file = file;
    }

    @Override
    public AppConfig load() {
        try (FileInputStream inputStream = new FileInputStream(this.file)) {
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream,AppConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("AppYamlFileLoader load error:" + e.getMessage(), e);
        }
    }
}
