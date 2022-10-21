package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.EngineType;

@Getter
@Setter
@Table("sys_table")
public class TableDO extends BaseDO {
    private String name;
    private String title;
    private String shortDescription;
    private String parent;
    private Boolean virtual;
    private String primary;
    private EngineType engine = EngineType.InnoDB;
    private String defaultCharset = "UTF-8";
}