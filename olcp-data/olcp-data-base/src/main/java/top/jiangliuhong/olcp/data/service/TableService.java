package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.consts.TableFieldNameConst;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.TableDO;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.po.TableFieldUpdateResPO;
import top.jiangliuhong.olcp.data.bean.po.TableFieldPO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.dao.TableRepository;
import top.jiangliuhong.olcp.data.exception.TableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private SystemTableProperties tableProperties;
    @Autowired
    private TableFieldService tableFieldService;

    @Transactional
    public void addTable(TablePO table) {
        // check table
        if (StringUtils.isBlank(table.getAppId())) {
            throw new TableException("请传入应用ID");
        }
        if (!CacheUtils.exist(CacheNames.APP_ID, table.getAppId())) {
            throw new TableException("请传入正确的应用ID");
        }
        for (SystemTableProperties.Table sysTable : tableProperties.getTables()) {
            if (StringUtils.equals(sysTable.getName(), table.getName())) {
                throw new TableException("表名不能为系统关键字");
            }
        }
        // 判断应用下表名是否已存在
        if (tableRepository.findByNameAndAppId(table.getName(), table.getAppId()) != null) {
            throw new TableException("当前应用下已存在数据表[" + table.getName() + "]");
        }
        if (StringUtils.isBlank(table.getTitle())) {
            table.setTitle(table.getName());
        }
        // add table
        TableDO saveTable = BeanUtils.copyBean(table, TableDO.class);
        tableRepository.save(saveTable);
        BeanUtils.copyProperties(saveTable, table);
        // add table field
        List<TableFieldDO> fields = BeanUtils.copyBean(table.getFields(), TableFieldDO.class);
        fields = tableFieldService.addField(saveTable, fields);
        table.setFields(BeanUtils.copyBean(fields, TableFieldPO.class));
        // TODO 索引
        // TODO 关系
        // TODO 创建默认列表
        // TODO 创建默认表单
        this.saveCache(table);
    }

    @Transactional
    public TableFieldUpdateResPO updateTable(TablePO table) {
        if (StringUtils.isBlank(table.getId())) {
            throw new TableException("请传入表格ID");
        }
        if (!CacheUtils.exist(CacheNames.TABLE_ID, table.getId())) {
            throw new TableException("请传入正确的表格ID");
        }
        // update table
        Optional<TableDO> tableOptional = tableRepository.findById(table.getId());
        TableDO tableDB = tableOptional.orElseThrow(() -> new TableException("not found table for " + table.getId()));
        String[] tableIgnore = ArrayUtils.addAll(TableFieldNameConst.BASE_FIELD, "name", "appId");
        BeanUtils.copyProperties(table, tableDB, tableIgnore);
        tableRepository.save(tableDB);
        BeanUtils.copyProperties(tableDB, table);
        List<TableFieldDO> fields = BeanUtils.copyBean(table.getFields(), TableFieldDO.class);
        TableFieldUpdateResPO res = tableFieldService.updateField(tableDB, fields);
        table.setFields(new ArrayList<>());
        table.getFields().addAll(BeanUtils.copyBean(res.getUpdates(), TableFieldPO.class));
        table.getFields().addAll(BeanUtils.copyBean(res.getCreates(), TableFieldPO.class));
        // TODO 索引
        // TODO 关系
        this.saveCache(table);
        return res;
    }

    public TablePO getTableInfo(String id) {
        Optional<TableDO> optionalTableDO = tableRepository.findById(id);
        if (optionalTableDO.isEmpty()) {
            return null;
        }
        TableDO tableDO = optionalTableDO.get();
        TablePO table = new TablePO();
        BeanUtils.copyProperties(tableDO, table);
        // query field
        List<TableFieldDO> fields = tableFieldService.getTableFields(id);
        List<TableFieldPO> fieldPOS = new ArrayList<>();
        fields.forEach(field -> {
            TableFieldPO tableFieldPO = new TableFieldPO();
            BeanUtils.copyProperties(field, tableFieldPO);
            fieldPOS.add(tableFieldPO);
        });
        table.setFields(fieldPOS);
        return table;
    }

    public List<TablePO> getAllTableByApp(String... appId) {
        List<TableDO> tableDOList = tableRepository.findAllByAppIdIn(appId);
        if (CollectionUtils.isEmpty(tableDOList)) {
            return new ArrayList<>();
        }
        List<String> tableIds = new ArrayList<>();
        tableDOList.forEach(f -> tableIds.add(f.getId()));
        List<TableFieldDO> tableFields = tableFieldService.getTableFields(tableIds.toArray(new String[0]));
        Map<String, List<TableFieldDO>> tableFieldMap = tableFields.stream().collect(Collectors.groupingBy(TableFieldDO::getTableId));
        tableDOList.forEach(tableDO -> {
            TablePO table = BeanUtils.copyBean(tableDO, TablePO.class);
            table.setFields(BeanUtils.copyBean(tableFieldMap.get(table.getId()), TableFieldPO.class));
        });
        return BeanUtils.copyBean(tableDOList, TablePO.class);
    }

    public void saveCache(String id) {
        TablePO tableInfo = getTableInfo(id);
        this.saveCache(tableInfo);
    }

    public void saveCache(TablePO table) {
        CacheUtils.putCacheValue(CacheNames.TABLE_ID, table.getId(), table);
        CacheUtils.putCacheValue(CacheNames.TABLE_NAME, table.getName(), table.getId());
    }

}
