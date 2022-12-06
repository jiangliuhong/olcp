package top.jiangliuhong.olcp.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
public class JwtProperties {
    private String tokenHeader = "Authorization";
    private String tokenHead = "Bearer ";
    private String secret = "olcp-secret";
    // 默认2小时，7200秒
    private Integer expiration = 7200;
}
