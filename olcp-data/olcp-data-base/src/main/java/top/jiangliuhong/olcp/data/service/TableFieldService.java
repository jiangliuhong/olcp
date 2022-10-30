package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.TableDO;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.dao.TableFieldRepository;
import top.jiangliuhong.olcp.data.type.FieldType;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableFieldService {

    @Autowired
    private TableFieldRepository tableFieldRepository;
    @Autowired
    private SystemTableProperties tableProperties;

    @Transactional
    public List<TableFieldDO> addField(TableDO table, List<TableFieldDO> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        List<TableFieldDO> defaultFields = this.getDefaultFields();
        Set<String> defaultFieldName = new HashSet<>();
        for (TableFieldDO defaultField : defaultFields) {
            defaultFieldName.add(defaultField.getName());
        }
        // 移除与系统字段同名的
        if (!fields.isEmpty()) {
            fields.removeIf(field -> defaultFieldName.contains(field.getName()));
        }
        // add system field
        fields.addAll(defaultFields);
        this.addFields(table, fields);
        return fields;
    }

    @Transactional
    public List<TableFieldDO> updateField(TableDO table, List<TableFieldDO> fields) {
        Map<String, String> existFieldNames = this.getExistFieldNames(table.getId());
        List<String> queryIds = new ArrayList<>();
        // 筛选：需要删除的字段、需要新增的字段、需要修改的字段
        for (TableFieldDO field : fields) {
            if (existFieldNames.containsKey(field.getName())) {
                existFieldNames.remove(field.getName());
            } else {
                if (StringUtils.isNotBlank(field.getId())) {
                    queryIds.add(field.getId());
                }
            }
        }

        if (!existFieldNames.isEmpty()) {
            List<String> ids = new ArrayList<>();
            existFieldNames.forEach((k, v) -> ids.add(v));
            tableFieldRepository.deleteAllById(ids);
        }

        List<TableFieldDO> existFields = tableFieldRepository.findAllByIdIn(queryIds);
        Set<String> existIds = existFields.stream()
                .collect(HashSet::new, (s, stu) -> s.add(stu.getId()), AbstractCollection::addAll);
        Set<String> existNames = existFields.stream()
                .collect(HashSet::new, (s, stu) -> s.add(stu.getId()), AbstractCollection::addAll);
        Map<String, List<TableFieldDO>> fieldMaps = fields.stream().collect(Collectors.groupingBy(field -> {
            if (StringUtils.isNotBlank(field.getId()) && existIds.contains(field.getId())) {
                return "update";
            } else {
                return "add";
            }
        }));
        List<TableFieldDO> addLists = fieldMaps.get("add");
        if (CollectionUtils.isNotEmpty(addLists)) {
            addLists.removeIf(f -> existNames.contains(f.getName()));
            this.addFields(table, addLists);
        }
        List<TableFieldDO> updateLists = fieldMaps.get("update");
        if (CollectionUtils.isNotEmpty(updateLists)) {
            Map<String, TableFieldDO> updateMaps
                    = updateLists.stream().collect(Collectors.toMap(TableFieldDO::getId, f -> f));
            for (TableFieldDO existField : existFields) {
                TableFieldDO updateInfo = updateMaps.get(existField.getId());
                updateInfo.setTableId(null);
                updateInfo.setName(null);
                updateInfo.setAppId(null);
                updateInfo.setSystemField(null);
                BeanUtils.copyNotNullProperties(updateInfo, existField);
            }
            tableFieldRepository.saveAll(existFields);
        }
        return fields;
    }

    private void addFields(TableDO table, List<TableFieldDO> fields) {
        // for each to fill data
        for (TableFieldDO field : fields) {
            field.setAppId(table.getAppId());
            field.setTableId(table.getId());
            if (field.getType() == null) {
                field.setType(FieldType.String);
            }
            if (field.getSn() == null) {
                field.setSn(100);
            }
        }
        fields.sort(Comparator.comparing(TableFieldDO::getSn));
        tableFieldRepository.saveAll(fields);
    }

    private List<TableFieldDO> getDefaultFields() {
        return tableProperties.getDefaultFields();
    }

    private Map<String, String> getExistFieldNames(String tableId) {
        Map<String, String> names = new HashMap<>();
        // TODO use cache
        List<TableFieldDO> existFields = tableFieldRepository.findUserField(tableId);
        for (TableFieldDO existField : existFields) {
            names.put(existField.getName(), existField.getId());
        }
        return names;
    }

}
