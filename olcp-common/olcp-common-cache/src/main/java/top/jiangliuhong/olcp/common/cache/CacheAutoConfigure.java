package top.jiangliuhong.olcp.common.cache;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

import java.util.List;

@Configuration
@Import({CacheProperties.class, CacheContext.class})
public class CacheAutoConfigure {

    @Bean
    public CacheFactory cacheFactory(List<ICacheFactory> factories) {
        return new CacheFactory(factories);
    }

}
