package top.jiangliuhong.olcp.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import top.jiangliuhong.olcp.common.config.YamlPropertySourceFactory;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;

@Configuration
@ComponentScan("top.jiangliuhong.olcp.data.service")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:data-base.yml")
@Import({SystemTableProperties.class})
public class DataBaseAutoConfigure {

}
