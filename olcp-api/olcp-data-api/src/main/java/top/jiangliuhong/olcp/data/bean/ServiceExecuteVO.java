package top.jiangliuhong.olcp.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Schema(title = "服务执行信息")
public class ServiceExecuteVO {

    @Schema(title = "类名")
    private String classname;
    @Schema(title = "方法名")
    private String method;
    @Schema(title = "执行参数")
    private Map<String, Object> parameters;
}
