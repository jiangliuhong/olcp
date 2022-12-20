package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询条件
 */
@Getter
@Setter
public class TablePageQueryParam {
    private Integer page;
    private Integer size;

    private List<SortData> orders;
}
