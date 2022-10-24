package top.jiangliuhong.olcp.web.handler;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.jiangliuhong.olcp.web.properties.ApiProperties;
import top.jiangliuhong.olcp.web.properties.SchemeProperties;

public class CommonApiInfo {

    @Autowired
    private ApiProperties apiProperties;

    /**
     * custom open api by api properties
     */
    public void customOpenAPI(OpenAPI openAPI) {
        Contact contact = new Contact();
        contact.email(apiProperties.getEmail());
        contact.name(apiProperties.getAuthor());
        contact.url(apiProperties.getUrl());
        Info info = new Info().title(apiProperties.getTitle());
        openAPI.info(info);

        SecurityRequirement securityRequirement = new SecurityRequirement();
        for (SchemeProperties scheme : apiProperties.getSchemes()) {
            if (StringUtils.isBlank(scheme.getName())) {
                continue;
            }
            SecurityScheme securityScheme = this.buildSecurityScheme(scheme);
            openAPI.schemaRequirement(scheme.getName(), securityScheme);
            if (scheme.getGlobal()) {
                securityRequirement.addList(scheme.getName());
            }
        }
        if (!securityRequirement.isEmpty()) {
            openAPI.addSecurityItem(securityRequirement);
        }
    }

    private SecurityScheme buildSecurityScheme(SchemeProperties properties) {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName(properties.getName());
        securityScheme.setType(properties.getType());
        securityScheme.setIn(properties.getIn());
        return securityScheme;
    }
}
