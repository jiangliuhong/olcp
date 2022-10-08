package top.jiangliuhong.olcp.engine.load.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.engine.bean.BusinessRule;

import java.util.List;

@Getter
@Setter
public class AppSource {
    private AppConfig config;
    private List<EntityInfo> entities;
    private List<ServiceInfo> serviceInfos;
    private List<RestInfo> restInfos;
    private List<BusinessRule> businessRules;
}
