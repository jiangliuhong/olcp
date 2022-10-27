package top.jiangliuhong.olcp.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "应用信息")
public class AppVO {
    @Schema(title = "应用名称")
    private String name;
    @Schema(title = "应用标题")
    private String title;
}
