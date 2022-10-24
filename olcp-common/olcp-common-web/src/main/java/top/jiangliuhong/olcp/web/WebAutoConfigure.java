package top.jiangliuhong.olcp.web;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.web.handler.CommonApiInfo;
import top.jiangliuhong.olcp.web.handler.ControllerExceptionAdvice;
import top.jiangliuhong.olcp.web.properties.ApiProperties;

@Configuration
@EnableConfigurationProperties({ApiProperties.class})
@Import({ControllerExceptionAdvice.class, CommonApiInfo.class})
public class WebAutoConfigure {

    @Autowired
    private CommonApiInfo commonApiInfo;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        commonApiInfo.customOpenAPI(openAPI);
        return openAPI;
    }

//    @Bean
//    public SpringDocConfigProperties

}
