package top.jiangliuhong.olcp.common.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "统一分页对象")
public class PageInfo<T> {
    private Integer page;
    private Integer size;
    private Integer total;
    private List<T> data;
}
