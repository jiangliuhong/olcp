package top.jiangliuhong.olcp.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "olcp.jwt")
public class JwtProperties {
    private String tokenHeader = "X-Token";
    private String tokenHead = "Bearer";
    private String secret = "olcp-secret";
    // 60*60*24 = 604800 second
    private Integer expiration = 604800;
}
