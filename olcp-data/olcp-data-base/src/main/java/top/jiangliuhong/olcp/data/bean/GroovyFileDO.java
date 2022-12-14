package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "sys_groovy_file")
public class GroovyFileDO extends BaseDO {
    private String name;
    private String folder;
    private String script;
    private String appId;

    public GroovyFileDO() {
    }

    public GroovyFileDO(String name, String folder, String appId) {
        this.name = name;
        this.folder = folder;
        this.appId = appId;
    }

    public GroovyFileDO(String name, String folder, String script, String appId) {
        this.name = name;
        this.folder = folder;
        this.script = script;
        this.appId = appId;
    }
}
