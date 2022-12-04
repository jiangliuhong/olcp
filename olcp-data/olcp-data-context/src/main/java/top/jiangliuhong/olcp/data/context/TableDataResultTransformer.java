package top.jiangliuhong.olcp.data.context;

import org.hibernate.transform.ResultTransformer;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;
import top.jiangliuhong.olcp.data.component.TableDefinition;
import top.jiangliuhong.olcp.data.component.TableFieldDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDataResultTransformer implements ResultTransformer {

    private final TableDefinition definition;

    public TableDataResultTransformer(TableDefinition definition) {
        this.definition = definition;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        StringObjectMap result = new StringObjectMap(tuple.length);
        List<TableFieldDefinition> fields = this.definition.getFields();
        Map<String, String> fieldNamesMap = new HashMap<>();
        fields.forEach(field -> fieldNamesMap.put(field.getDbName(), field.getName()));
        for (int i = 0; i < tuple.length; i++) {
            String alias = aliases[i];
            if (alias != null) {
                String name = fieldNamesMap.get(alias);
                if (name != null && !name.equals("")) {
                    result.put(name, tuple[i]);
                }
            }
        }
        return result;
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
