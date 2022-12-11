package top.jiangliuhong.olcp.common.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePageQuery {
    private Integer page = 1;
    private Integer size = 10;
}
