package top.jiangliuhong.olcp.common.handler;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import top.jiangliuhong.olcp.common.utils.SpringUtils;

/**
 * common entity listener
 */
public class CommonEntityListener {

    private final EntityInterceptorConfig config;

    public CommonEntityListener() {
        this.config = SpringUtils.getBean(EntityInterceptorConfig.class);
    }

    @PrePersist
    public void prePersist(Object object) {
        this.config.intercept(object, ins -> ins.prePersist(object));
    }

    @PostPersist
    public void postPersist(Object object) {
        this.config.intercept(object, ins -> ins.postPersist(object));
    }

    @PreUpdate
    public void preUpdate(Object object) {
        this.config.intercept(object, ins -> ins.preUpdate(object));
    }

    @PostUpdate
    public void postUpdate(Object object) {
        this.config.intercept(object, ins -> ins.postUpdate(object));
    }

    @PreRemove
    public void preRemove(Object object) {
        this.config.intercept(object, ins -> ins.preRemove(object));
    }

    @PostRemove
    public void postRemove(Object object) {
        this.config.intercept(object, ins -> ins.postRemove(object));
    }
}
