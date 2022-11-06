package top.jiangliuhong.olcp.data.proxy;

import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.utils.NameUtils;
import top.jiangliuhong.olcp.data.bean.po.AppPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.CacheNames;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * table proxy ,add extend function
 */
public class TableProxy {

    private final TablePO source;
    private final AppPO app;

    public TableProxy(TablePO table) {
        this.source = table;
        this.app = CacheUtils.getCacheValue(CacheNames.APP_ID, this.source.getAppId());
    }

    public TablePO getSource() {
        return this.source;
    }

    public String getName() {
        return NameUtils.camelToUnderline(this.app.getName()) + "_" + NameUtils.camelToUnderline(this.source.getName());
    }

    public List<FieldProxy> getFields() {
        List<FieldProxy> list = new ArrayList<>();
        this.source.getFields().forEach(field -> list.add(new FieldProxy(field)));
        return list;
    }

    public void eachFields(Consumer<FieldProxy> fieldProxyConsumer) {
        this.source.getFields().forEach(field -> fieldProxyConsumer.accept(new FieldProxy(field)));
    }
}
