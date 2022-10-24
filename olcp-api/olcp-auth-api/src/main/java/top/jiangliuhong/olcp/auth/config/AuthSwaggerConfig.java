package top.jiangliuhong.olcp.auth.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.jiangliuhong.olcp.web.handler.CommonApiInfo;

@Configuration
public class AuthSwaggerConfig {

    @Autowired
    private CommonApiInfo commonApiInfo;

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/v1/auth/**")
                .packagesToScan("top.jiangliuhong.olcp.auth.api")
                .addOpenApiCustomiser(openApi -> {
                            commonApiInfo.customOpenAPI(openApi);
                            Info info = openApi.getInfo();
                            info.title(info.getTitle() + " Auth")
                                    .version("1.0.0")
                                    .description("olcp auth rest api");
                        }
                )
                .build();
    }

}
