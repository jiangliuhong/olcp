package top.jiangliuhong.olcp.common.cache;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.common.cache.component.CaffeineCacheFactory;
import top.jiangliuhong.olcp.common.cache.properties.CacheProperties;

import java.util.List;

@Configuration
@Import({CacheProperties.class, CacheContext.class})
public class CacheAutoConfigure {

    @Bean
    public ICacheFactory caffeineCacheFactory() {
        return new CaffeineCacheFactory();
    }

    @Bean
    public CacheFactory cacheFactory(List<ICacheFactory> factories) {
        return new CacheFactory(factories);
    }

}
