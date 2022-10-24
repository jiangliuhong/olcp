package top.jiangliuhong.olcp.web.properties;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "olcp.common.api")
public class ApiProperties {

    private String title = "OLCP";
    private String author = "jarome";
    private String email = "ja_rome@163.com";
    private String url;

    private List<SchemeProperties> schemes = new ArrayList<>() {{
        add(new SchemeProperties()
                .name(HttpHeaders.AUTHORIZATION)
                .global(true)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER));
    }};
}
