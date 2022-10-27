package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.EngineType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_table")
public class TableDO extends BaseDO {
    private String name;
    private String title;
    private String appId;
    private String shortDescription;
    private String parent;
    private Boolean virtualTable;
    private EngineType engineType = EngineType.InnoDB;
    private String defaultCharset = "UTF-8";
}
