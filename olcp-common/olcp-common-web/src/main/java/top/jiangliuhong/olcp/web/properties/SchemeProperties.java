package top.jiangliuhong.olcp.web.properties;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemeProperties {

    private String name;
    private SecurityScheme.Type type;
    private SecurityScheme.In in = SecurityScheme.In.HEADER;
    private Boolean global = false;

    public SchemeProperties name(String name) {
        this.name = name;
        return this;
    }

    public SchemeProperties global(Boolean global) {
        this.global = global;
        return this;
    }

    public SchemeProperties type(SecurityScheme.Type type) {
        this.type = type;
        return this;
    }

    public SchemeProperties in(SecurityScheme.In in) {
        this.in = in;
        return this;
    }
}
