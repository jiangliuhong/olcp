package top.jiangliuhong.olcp.data.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询条件
 */
@Getter
@Setter
public class TablePageQueryParam {
    private Integer page;
    private Integer size;
}
