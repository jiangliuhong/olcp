package top.jiangliuhong.olcp.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.jiangliuhong.olcp.auth.properties.JwtProperties;

@EnableConfigurationProperties({JwtProperties.class})
public class AuthAutoConfigure {
}
