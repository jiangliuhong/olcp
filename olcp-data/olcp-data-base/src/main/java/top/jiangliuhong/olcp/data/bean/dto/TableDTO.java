package top.jiangliuhong.olcp.data.bean.dto;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.bean.TableDO;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.TableIndexDO;
import top.jiangliuhong.olcp.data.bean.TableRelationshipDO;

import java.util.List;

@Getter
@Setter
public class TableDTO extends TableDO {
    private List<TableFieldDO> fields;
    private List<TableRelationshipDO> relationships;
    private List<TableIndexDO> indexes;
}
