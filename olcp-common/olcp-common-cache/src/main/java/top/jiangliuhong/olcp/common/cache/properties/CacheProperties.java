package top.jiangliuhong.olcp.common.cache.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "olcp.cache")
public class CacheProperties {

    private List<Group> groups = new ArrayList<>();

    @Getter
    @Setter
    public static class Group {
        private String name;
        // 写入或者更新后缓存过期失效时间
        private Integer expireAfterWrite;
        // 操作后缓存过期失效时间，存在expireAfterWrite时，改属性无效
        private Integer expireAfterAccess;
        // 初始的缓存空间大小
        private Integer initialCapacity;
        // 缓存的最大条数
        private Integer maximumSize;
    }

}
