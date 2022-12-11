package top.jiangliuhong.olcp.data.run;

import top.jiangliuhong.olcp.common.consts.SystemDataConst;
import top.jiangliuhong.olcp.data.bean.po.AppPO;

import java.util.ArrayList;
import java.util.List;

public class DataSystemAppRegister {

    public static final String SYS_APP_NAME = SystemDataConst.SYSTEM_NAME;
    public static final String SYS_APP_ID = SYS_APP_NAME + "_app";

    public List<AppPO> getRegisterBean() {
        AppPO appPO = new AppPO();
        appPO.setTitle("系统应用");
        appPO.setName(SYS_APP_NAME);
        appPO.setId(SYS_APP_ID);
        List<AppPO> list = new ArrayList<>();
        list.add(appPO);
        return list;
    }
}
