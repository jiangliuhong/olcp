package top.jiangliuhong.olcp.data.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "应用修改信息")
public class AppUpdateVO {
    @Schema(title = "应用标题")
    private String title;
}
