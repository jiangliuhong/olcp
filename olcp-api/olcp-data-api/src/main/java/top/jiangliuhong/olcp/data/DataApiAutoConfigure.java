package top.jiangliuhong.olcp.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.data.config.DataSwaggerConfig;

@Configuration
@Import(DataSwaggerConfig.class)
@ComponentScan("top.jiangliuhong.olcp.data.api")
public class DataApiAutoConfigure {

}
