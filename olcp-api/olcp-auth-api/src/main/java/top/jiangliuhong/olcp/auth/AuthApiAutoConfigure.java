package top.jiangliuhong.olcp.auth;

import org.springframework.context.annotation.Bean;
import top.jiangliuhong.olcp.auth.api.UserAuthApi;

public class AuthApiAutoConfigure {
    @Bean
    public UserAuthApi userAuthApi() {
        return new UserAuthApi();
    }
}
