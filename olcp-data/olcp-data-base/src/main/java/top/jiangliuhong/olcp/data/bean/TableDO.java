package top.jiangliuhong.olcp.data.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import top.jiangliuhong.olcp.common.annotation.OlcpField;
import top.jiangliuhong.olcp.common.bean.BaseDO;
import top.jiangliuhong.olcp.data.type.EngineType;

@Getter
@Setter
@Entity
@Table(name = "sys_table")
public class TableDO extends BaseDO {

    @OlcpField(title = "表名", type = "String")
    private String name;

    @OlcpField(title = "显示名", type = "String")
    private String title;

    @OlcpField(title = "应用", type = "SysId")
    private String appId;

    @OlcpField(title = "简介", type = "String")
    private String shortDescription;

    @OlcpField(title = "父表", type = "Reference.system.sys_table")
    private String parent;

    @OlcpField(title = "是否虚拟表", type = "Boolean")
    private Boolean virtualTable = false;

    @OlcpField(title = "数据库引擎类型", type = "String")
    private EngineType engineType = EngineType.InnoDB;

    @OlcpField(title = "默认编码格式", type = "String")
    private String defaultCharset = "utf8";
}
