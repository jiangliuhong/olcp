package top.jiangliuhong.olcp.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.common.bean.BasePageQuery;

import java.util.List;

@Getter
@Setter
@Schema(title = "应用表格分页查询条件")
public class AppTableQuery extends BasePageQuery {

    private List<String> orderBy;
}
