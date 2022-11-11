package top.jiangliuhong.olcp.data.bean.po;

import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TableFieldUpdateResPO {
    private List<TableFieldDO> updates = new ArrayList<>();
    private List<TableFieldDO> creates = new ArrayList<>();
    private List<String> deletes = new ArrayList<>();
}
