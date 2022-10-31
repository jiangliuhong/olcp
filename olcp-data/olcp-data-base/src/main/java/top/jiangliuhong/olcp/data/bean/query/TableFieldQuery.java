package top.jiangliuhong.olcp.data.bean.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TableFieldQuery {

    private String tableId;
    private Boolean systemField;
    private Boolean userField;

}
