package top.jiangliuhong.olcp.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "应用新增信息")
public class AppCreateVO {
    @Schema(title = "应用名称")
    private String name;
    @Schema(title = "应用标题")
    private String title;
}
