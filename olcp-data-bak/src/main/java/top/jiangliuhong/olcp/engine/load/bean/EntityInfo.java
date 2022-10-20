package top.jiangliuhong.olcp.engine.load.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntityInfo {
    private String name;
    private String title;
    private String shortDescription;
    private String parent;
    private List<FieldInfo> fieldInfos;
    private Boolean virtual;
    private FieldInfo primary;
}
