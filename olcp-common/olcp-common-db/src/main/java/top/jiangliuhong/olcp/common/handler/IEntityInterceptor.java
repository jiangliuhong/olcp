package top.jiangliuhong.olcp.common.handler;

public interface IEntityInterceptor<T> {

    public void prePersist(T entity);

    public void postPersist(T entity);

    public void preUpdate(T entity);

    public void postUpdate(T entity);

    public void preRemove(T entity);

    public void postRemove(T entity);

}
