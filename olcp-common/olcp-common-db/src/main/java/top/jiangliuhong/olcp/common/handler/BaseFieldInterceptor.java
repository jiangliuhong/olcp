package top.jiangliuhong.olcp.common.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.common.utils.IdUtils;

import java.util.Date;

@Order(1)
public class BaseFieldInterceptor extends AbstractEntityInterceptor<BaseDO> {

    @Override
    public void prePersist(BaseDO entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(IdUtils.generate());
        }
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
    }

    @Override
    public void preUpdate(BaseDO entity) {
        entity.setUpdateTime(new Date());
    }
}
