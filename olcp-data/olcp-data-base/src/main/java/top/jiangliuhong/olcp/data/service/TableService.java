package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.TableDO;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.cache.TableCachePO;
import top.jiangliuhong.olcp.data.bean.cache.TableFieldCachePO;
import top.jiangliuhong.olcp.data.bean.dto.TableDTO;
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
    public String addTable(TableDTO table) {
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
        TableDO saveTable = new TableDO();
        BeanUtils.copyProperties(table, saveTable);
        tableRepository.save(saveTable);
        String tableId = saveTable.getId();
        table.setId(tableId);
        List<TableFieldDO> fields = tableFieldService.addField(table, table.getFields());
        table.setFields(fields);
        // TODO 创建默认列表
        // TODO 创建默认表单
        return tableId;
    }

    @Transactional
    public void updateTable(TableDTO table) {
        if (StringUtils.isBlank(table.getId())) {
            throw new TableException("请传入系统ID");
        }
        // TODO use cache
        if (!tableRepository.existsById(table.getId())) {
            throw new TableException("请传入正确的系统ID");
        }
        // 将不允许修改的字段设置为null
        table.setName(null);
        table.setAppId(null);
        table.setVirtualTable(null);
        // update table
        Optional<TableDO> tableOptional = tableRepository.findById(table.getId());
        TableDO tableDB = tableOptional.orElseThrow(() -> new TableException("not found table for " + table.getId()));
        BeanUtils.copyNotNullProperties(table, tableDB);
        tableRepository.save(tableDB);
        tableFieldService.updateField(tableDB, table.getFields());
    }

    public TableDTO getTableInfo(String id) {
        Optional<TableDO> optionalTableDO = tableRepository.findById(id);
        if (optionalTableDO.isEmpty()) {
            return null;
        }
        TableDO tableDO = optionalTableDO.get();
        TableDTO table = new TableDTO();
        BeanUtils.copyProperties(tableDO, table);
        // query field
        List<TableFieldDO> fields = tableFieldService.getTableFields(id);
        table.setFields(fields);
        return table;
    }

    public List<TableDTO> getAllTableByApp(String... appId) {
        List<TableDO> tableDOList = tableRepository.findAllByAppIdIn(appId);
        if (CollectionUtils.isEmpty(tableDOList)) {
            return new ArrayList<>();
        }
        List<TableDTO> list = new ArrayList<>();
        List<String> tableIds = new ArrayList<>();
        tableDOList.forEach(f -> tableIds.add(f.getId()));
        List<TableFieldDO> tableFields = tableFieldService.getTableFields(tableIds.toArray(new String[0]));
        Map<String, List<TableFieldDO>> tableFieldMap = tableFields.stream().collect(Collectors.groupingBy(TableFieldDO::getTableId));
        tableDOList.forEach(tableDO -> {
            TableDTO table = new TableDTO();
            BeanUtils.copyProperties(tableDO, table);
            table.setFields(tableFieldMap.get(table.getId()));
        });
        return list;
    }

    public void saveCache(TableDTO table) {
        TableCachePO tableCache = new TableCachePO();
        BeanUtils.copyProperties(table, tableCache);
        if (CollectionUtils.isNotEmpty(table.getFields())) {
            List<TableFieldCachePO> tableFieldCaches = new ArrayList<>();
            table.getFields().forEach(field -> {
                TableFieldCachePO tfc = new TableFieldCachePO();
                BeanUtils.copyProperties(field, tfc);
                tableFieldCaches.add(tfc);
            });
            tableCache.setFields(tableFieldCaches);
        }
        CacheUtils.putCacheValue(CacheNames.TABLE_ID, tableCache.getId(), tableCache);
        CacheUtils.putCacheValue(CacheNames.TABLE_NAME, tableCache.getName(), tableCache.getId());
    }


}
