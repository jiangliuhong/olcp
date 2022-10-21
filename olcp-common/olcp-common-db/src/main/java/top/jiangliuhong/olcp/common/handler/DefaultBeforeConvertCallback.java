package top.jiangliuhong.olcp.common.handler;

import org.apache.commons.lang3.StringUtils;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.common.utils.IdUtils;

import java.util.Date;

public class DefaultBeforeConvertCallback {

    public BaseDO onBeforeConvert(BaseDO aggregate) {
        Date nowDate = new Date();
        if (StringUtils.isBlank(aggregate.getId())) {
            // created
            aggregate.setId(IdUtils.generate());
            aggregate.setCreateTime(nowDate);
        }
        aggregate.setUpdateTime(nowDate);
        return aggregate;
    }
}
