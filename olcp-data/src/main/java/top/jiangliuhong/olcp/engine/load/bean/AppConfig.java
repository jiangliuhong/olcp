package top.jiangliuhong.olcp.engine.load.bean;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.engine.type.ScriptType;

@Setter
@Getter
public class AppConfig {
    private String name;
    private String title;
    private ScriptType script;
    private String version;
}
