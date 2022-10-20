package top.jiangliuhong.olcp.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;

@ComponentScan
public class WebAutoConfigure {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("ja_rome@163.com");

        OpenAPI openAPI = new OpenAPI().info(new Info().title("OLCP"));
        // oauth2.0 password
        openAPI.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());
        //全局安全校验项，也可以在对应的controller上加注解SecurityRequirement
        openAPI.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));

        return openAPI;
    }

    private SecurityScheme securityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        //类型
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        //请求头的name
        securityScheme.setName(HttpHeaders.AUTHORIZATION);
        //token所在未知
        securityScheme.setIn(SecurityScheme.In.HEADER);
        return securityScheme;
    }
}
