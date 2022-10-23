package top.jiangliuhong.olcp.auth.handler;

import org.springframework.core.annotation.Order;

import top.jiangliuhong.olcp.auth.bean.SimpleUserDO;
import top.jiangliuhong.olcp.auth.utils.AuthUtils;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.common.handler.AbstractEntityInterceptor;

/**
 * modify create user and update user
 */
@Order(2)
public class BaseAuthInterceptor extends AbstractEntityInterceptor<BaseDO> {

    @Override
    public void prePersist(BaseDO entity) {
        SimpleUserDO currentUser = AuthUtils.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getId();
            entity.setCreateUser(userId);
            entity.setUpdateUser(userId);
        }
    }

    @Override
    public void preUpdate(BaseDO entity) {
        SimpleUserDO currentUser = AuthUtils.getCurrentUser();
        if (currentUser != null) {
            entity.setUpdateUser(currentUser.getId());
        }
    }

}
