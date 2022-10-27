package top.jiangliuhong.olcp.data.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.dto.TableDTO;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.dao.TableFieldRepository;
import top.jiangliuhong.olcp.data.dao.TableRepository;
import top.jiangliuhong.olcp.data.exception.TableException;
import top.jiangliuhong.olcp.data.type.FieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private TableFieldRepository tableFieldRepository;
    @Autowired
    private SystemTableProperties tableProperties;

    @Transactional
    public void addTable(TableDTO table) {
        // check table
        this.checkTable(table);
        // add table
        tableRepository.save(table);
        List<TableFieldDO> fields = table.getFields();
        if (fields == null) {
            fields = new ArrayList<>();
        }
        // add system field
        fields.addAll(this.getDefaultField());
        // for each to fill data
        for (TableFieldDO field : fields) {
            field.setAppId(table.getAppId());
            if (field.getType() == null) {
                field.setType(FieldType.String);
            }
        }
        tableFieldRepository.saveAll(fields);
    }

    @Transactional
    public void updateTable(TableDTO table) {
        if (StringUtils.isBlank(table.getId())) {
            throw new TableException("请传入系统ID");
        }
        // check id is correct
        // TODO use cache
        if (!tableRepository.existsById(table.getId())) {
            throw new TableException("请传入正确的系统ID");
        }
        // update table
        this.checkTable(table);
        tableRepository.save(table);
        // check field
        Map<String, String> existFieldNames = this.getExistFieldNames(table.getId());
        for (TableFieldDO field : table.getFields()) {
            existFieldNames.remove(field.getName());
            field.setAppId(table.getAppId());
            field.setTableId(table.getId());
            if (field.getType() == null) {
                field.setType(FieldType.String);
            }
        }
        if (!existFieldNames.isEmpty()) {
            List<String> ids = new ArrayList<>();
            existFieldNames.forEach((k, v) -> ids.add(v));
            tableFieldRepository.deleteAllById(ids);
        }
        tableFieldRepository.saveAll(table.getFields());
    }

    private Map<String, String> getExistFieldNames(String tableId) {
        Map<String, String> names = new HashMap<>();
        // TODO use cache
        List<TableFieldDO> existFields = tableFieldRepository.findAllByTableIdAndSystemFieldIsTrue(tableId);
        for (TableFieldDO existField : existFields) {
            names.put(existField.getName(), existField.getId());
        }
        return names;
    }

    private void checkTable(TableDTO table) {
        // TODO check app id
        // check table name
        for (SystemTableProperties.Table sysTable : tableProperties.getTables()) {
            if (StringUtils.equals(sysTable.getName(), table.getName())) {
                throw new TableException("表名不能为系统关键字");
            }
        }
        if (StringUtils.isBlank(table.getTitle())) {
            table.setTitle(table.getName());
        }
    }

    private List<TableFieldDO> getDefaultField() {
        return tableProperties.getDefaultFields();
    }
}
