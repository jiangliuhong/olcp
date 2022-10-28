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

    private List<CacheInfo> groups = new ArrayList<>();

}
