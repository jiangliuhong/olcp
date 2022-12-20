package top.jiangliuhong.olcp.data.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.consts.Sorts;

@Getter
@Setter
@AllArgsConstructor
public class SortData {
    private String name;
    private Sorts type;
}
