package top.jiangliuhong.olcp.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import top.jiangliuhong.olcp.common.config.YamlPropertySourceFactory;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.run.DataCacheRegister;

@Configuration
@ComponentScan("top.jiangliuhong.olcp.data.service")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:data-base.yml")
@Import({SystemTableProperties.class, DataCacheRegister.class})
public class DataBaseAutoConfigure {

}
