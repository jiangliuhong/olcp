package top.jiangliuhong.olcp.auth.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import top.jiangliuhong.olcp.auth.bean.UserDO;
import top.jiangliuhong.olcp.auth.utils.AuthUtils;
import top.jiangliuhong.olcp.common.bean.BaseDO;

/**
 * modify create user and update user
 */
public class AuthBeforeConvertCallback implements BeforeConvertCallback<BaseDO> {

    @Override
    public BaseDO onBeforeConvert(BaseDO aggregate) {
        UserDO currentUser = AuthUtils.getCurrentUser();
        if (currentUser != null) {
            if (StringUtils.isBlank(aggregate.getId())) {
                aggregate.setCreateUser(aggregate.getId());
            }
            aggregate.setUpdateUser(aggregate.getId());
        }
        return aggregate;
    }
}
