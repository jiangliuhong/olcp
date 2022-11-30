package top.jiangliuhong.olcp.data.context;

import lombok.extern.log4j.Log4j2;
import top.jiangliuhong.olcp.common.utils.SpringUtils;
import top.jiangliuhong.olcp.data.entity.TableEntity;

@Log4j2
public final class Context {

    public static AppContext getAppContext() {
        return AppContextHolder.getCurrentAppContext();
    }

    public static TableEntity getTable() {
        AppContext appContext = getAppContext();
        return getTableExecutionContextFactory().table(appContext.getName());
    }

    private static final class TableExecutionContextFactoryHolder {
        private static final TableExecutionContextFactory tableExecutionContextFactory = SpringUtils.getBean(TableExecutionContextFactory.class);
    }

    public static TableExecutionContextFactory getTableExecutionContextFactory() {
        return TableExecutionContextFactoryHolder.tableExecutionContextFactory;
    }
}
