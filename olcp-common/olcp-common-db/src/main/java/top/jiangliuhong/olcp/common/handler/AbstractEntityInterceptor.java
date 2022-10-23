package top.jiangliuhong.olcp.common.handler;

import top.jiangliuhong.olcp.common.bean.BaseDO;

public abstract class AbstractEntityInterceptor<T extends BaseDO> implements IEntityInterceptor<T> {

    @Override
    public void prePersist(T entity) {

    }

    @Override
    public void postPersist(T entity) {

    }

    @Override
    public void preUpdate(T entity) {

    }

    @Override
    public void postUpdate(T entity) {

    }

    @Override
    public void preRemove(T entity) {

    }

    @Override
    public void postRemove(T entity) {

    }
}
