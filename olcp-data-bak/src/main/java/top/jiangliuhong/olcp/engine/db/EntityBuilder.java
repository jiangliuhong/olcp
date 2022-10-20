package top.jiangliuhong.olcp.engine.db;

import top.jiangliuhong.olcp.engine.load.bean.EntityInfo;

public class EntityBuilder extends EntityInfo {

    private EntityInfo entityInfo;

    public EntityBuilder(EntityInfo entityInfo) {
        this.setName(entityInfo.getName());
        //this.setAppid(entity.getAppid());
        this.setFieldInfos(entityInfo.getFieldInfos());
        this.setParent(entityInfo.getParent());
        this.setTitle(entityInfo.getTitle());
        this.setShortDescription(entityInfo.getShortDescription());
        this.setVirtual(entityInfo.getVirtual());
    }

    @Override
    public String getName() {
        // TODO add app name
        return this.entityInfo.getName();
    }

    @Override
    public String getParent() {
        return super.getParent();
    }
}
