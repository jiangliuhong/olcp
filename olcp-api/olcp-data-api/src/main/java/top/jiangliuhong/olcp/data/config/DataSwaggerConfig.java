package top.jiangliuhong.olcp.data.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import top.jiangliuhong.olcp.web.handler.CommonApiInfo;

@Configuration
public class DataSwaggerConfig {

    @Autowired
    private CommonApiInfo commonApiInfo;

    @Bean
    public GroupedOpenApi dataApi() {
        return GroupedOpenApi.builder()
                .group("Data")
                .pathsToMatch("/api/v1/data/**")
                .packagesToScan("top.jiangliuhong.olcp.data.api")
                .addOpenApiCustomiser(openApi -> {
                            commonApiInfo.customOpenAPI(openApi);
                            Info info = openApi.getInfo();
                            info.title(info.getTitle() + " Data")
                                    .version("1.0.0")
                                    .description("olcp  rest api");
                        }
                )
                .build();
    }
}
