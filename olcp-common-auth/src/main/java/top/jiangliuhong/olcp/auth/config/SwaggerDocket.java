package top.jiangliuhong.olcp.auth.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocket {

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .packagesToScan("top.jiangliuhong.olcp.auth.api")
                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Actuator API").version("1.0.0")))
                .build();
    }
}
