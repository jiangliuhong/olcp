package top.jiangliuhong.olcp.data.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.jiangliuhong.olcp.common.cache.CacheUtils;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.TableDO;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.dto.TableDTO;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.consts.CacheNames;
import top.jiangliuhong.olcp.data.dao.TableRepository;
import top.jiangliuhong.olcp.data.exception.TableException;

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
        if (!CacheUtils.exist(CacheNames.APP, table.getAppId())) {
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
        tableFieldService.updateField(table, table.getFields());
    }

}
