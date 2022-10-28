package top.jiangliuhong.olcp.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "olcp.auth")
public class AuthProperties {
    private List<String> ignoreUrls = new ArrayList<>();
    private JwtProperties jwt = new JwtProperties();
    // 360 秒
    private Integer userCacheExpire = 360;

    public List<String> getIgnoreUrls() {
        // add default urls
        List<String> list = new ArrayList<>() {{
            add("/api/v1/auth/login");
            add("/swagger-resources/**");
            add("/swagger-ui.html");
            add("/swagger-ui/**");
            add("/**/v2/api-docs");
            add("/api-docs/**");
        }};
        list.addAll(this.ignoreUrls);
        return list;
    }
}