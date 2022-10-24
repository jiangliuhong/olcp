package top.jiangliuhong.olcp.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.auth.api.UserAuthApi;
import top.jiangliuhong.olcp.auth.config.AuthSwaggerConfig;

@Import({AuthSwaggerConfig.class})
public class AuthApiAutoConfigure {
    @Bean
    public UserAuthApi userAuthApi() {
        return new UserAuthApi();
    }
}
