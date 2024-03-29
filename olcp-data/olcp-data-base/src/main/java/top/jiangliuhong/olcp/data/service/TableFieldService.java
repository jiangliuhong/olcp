package top.jiangliuhong.olcp.data.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.common.consts.TableFieldNameConst;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.TableDO;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.po.TableFieldUpdateResPO;
import top.jiangliuhong.olcp.data.bean.query.TableFieldQuery;
import top.jiangliuhong.olcp.data.config.properties.SystemTableProperties;
import top.jiangliuhong.olcp.data.dao.TableFieldRepository;
import top.jiangliuhong.olcp.data.exception.TableException;
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

    public List<TableFieldDO> getTableFields(String tableId) {
        TableFieldQuery query = TableFieldQuery.builder().tableId(tableId).build();
        return tableFieldRepository.findAllByQuery(query);
    }

    public List<TableFieldDO> getTableFields(String... tableIds) {
        TableFieldQuery query = TableFieldQuery.builder().tableIds(tableIds).build();
        return tableFieldRepository.findAllByQuery(query);
    }

    @Transactional
    public List<TableFieldDO> addField(TableDO table, List<TableFieldDO> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        this.checkField(fields);
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
    public TableFieldUpdateResPO updateField(TableDO table, List<TableFieldDO> fields) {
        List<TableFieldDO> defaultFields = this.getDefaultFields();
        Set<String> defaultFieldName = new HashSet<>();
        for (TableFieldDO defaultField : defaultFields) {
            defaultFieldName.add(defaultField.getName());
        }
        if (!fields.isEmpty()) {
            fields.removeIf(field -> defaultFieldName.contains(field.getName()));
        }
        this.checkField(fields);
        TableFieldUpdateResPO res = new TableFieldUpdateResPO();
        Map<String, String> existFieldNames = this.getExistFieldNames(table.getId());
        List<String> queryNames = new ArrayList<>();
        // 筛选：需要删除的字段、需要新增的字段、需要修改的字段
        for (TableFieldDO field : fields) {
            existFieldNames.remove(field.getName());
            if (StringUtils.isNotBlank(field.getId())) {
                queryNames.add(field.getName());
            }
        }
        if (!existFieldNames.isEmpty()) {
            List<String> deleteIds = new ArrayList<>();
            List<String> deleteNames = new ArrayList<>();
            existFieldNames.forEach((k, v) -> {
                deleteIds.add(v);
                deleteNames.add(k);
            });
            tableFieldRepository.deleteAllById(deleteIds);
            res.getDeletes().addAll(deleteNames);
        }

        List<TableFieldDO> existFields = tableFieldRepository.findAllByTableIdAndNameIn(table.getId(), queryNames);
        Set<String> existNames = existFields.stream()
                .collect(HashSet::new, (s, stu) -> s.add(stu.getName()), AbstractCollection::addAll);
        Map<String, List<TableFieldDO>> fieldMaps = fields.stream().collect(Collectors.groupingBy(field -> {
            if (existNames.contains(field.getName())) {
                return "update";
            } else {
                return "add";
            }
        }));
        List<TableFieldDO> addLists = fieldMaps.get("add");
        if (CollectionUtils.isNotEmpty(addLists)) {
            this.addFields(table, addLists);
            res.getCreates().addAll(addLists);
        }
        List<TableFieldDO> updateLists = fieldMaps.get("update");
        if (CollectionUtils.isNotEmpty(updateLists)) {
            Map<String, TableFieldDO> updateMaps
                    = updateLists.stream().collect(Collectors.toMap(TableFieldDO::getName, f -> f));
            for (TableFieldDO existField : existFields) {
                TableFieldDO updateInfo = updateMaps.get(existField.getName());
                if (this.needUpdateDDL(existField, updateInfo)) {
                    res.getUpdateForDDL().add(existField);
                }
                String[] tableIgnore = ArrayUtils.addAll(TableFieldNameConst.BASE_FIELD, "name", "appId", "systemField", "tableId");
                BeanUtils.copyNotNullProperties(updateInfo, existField, tableIgnore);
            }
            tableFieldRepository.saveAll(existFields);
            res.getUpdates().addAll(existFields);
        }
        return res;
    }

    private boolean needUpdateDDL(TableFieldDO db, TableFieldDO update) {
        boolean need = false;
        if (StringUtils.isNotBlank(update.getName()) && !StringUtils.equals(update.getName(), db.getName())) {
            need = true;
        }
        if (StringUtils.isNotBlank(update.getTitle()) && !StringUtils.equals(update.getTitle(), db.getTitle())) {
            need = true;
        }
        if (update.getType() != null && update.getType() != db.getType()) {
            need = true;
        }
        if (update.getMaxLength() != null && !update.getMaxLength().equals(db.getMaxLength())) {
            need = true;
        }
        if (update.getMaxPrecision() != null && !update.getMaxPrecision().equals(db.getMaxPrecision())) {
            need = true;
        }
        if (update.getRequired() != null && update.getRequired() != db.getRequired()) {
            need = true;
        }
        if (update.getUniqueness() != null && update.getUniqueness() != db.getUniqueness()) {
            need = true;
        }
        return need;
    }

    private void checkField(List<TableFieldDO> fields) {
        // 判断名称是否为空
        // 判断是否有重名的
        Set<String> fieldNames = new HashSet<>();
        fields.forEach(field -> {
            if (StringUtils.isBlank(field.getName())) {
                throw new TableException("名称不能为空");
            }
            if (fieldNames.contains(field.getName())) {
                throw new TableException("名称重复:" + field.getName());
            } else {
                fieldNames.add(field.getName());
            }
        });
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
        TableFieldQuery query = TableFieldQuery.builder().tableId(tableId).userField(true).build();
        List<TableFieldDO> existFields = tableFieldRepository.findAllByQuery(query);
        for (TableFieldDO existField : existFields) {
            names.put(existField.getName(), existField.getId());
        }
        return names;
    }

}
