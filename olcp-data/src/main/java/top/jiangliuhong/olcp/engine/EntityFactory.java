package top.jiangliuhong.olcp.engine;

import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.engine.load.bean.EntityInfo;

import javax.annotation.PostConstruct;

@Component
public class EntityFactory {

    @PostConstruct
    private void init() {

    }

    public EntityFacade create(EntityInfo entityInfo) {
        EntityFacade entity = new EntityFacade();
        return entity;
    }

}
