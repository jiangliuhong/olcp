package top.jiangliuhong.olcp.data.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.data.bean.po.ServicePO;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ServiceService {

    public List<ServicePO> getAllServiceByApp(String... appIds) {
        List<ServicePO> list = new ArrayList<>();
        ServicePO service = new ServicePO();
        service.setAppId("test-app");
        service.setName("TestUser");
        service.setScript("package olcp.test;\n" +
                "\n" +
                "class User {\n" +
                "    def getUserInfo() {\n" +
                "        def info = [:]\n" +
                "        info.put(\"id\", \"1\");\n" +
                "        info.put(\"name\", \"test\");\n" +
                "        return info\n" +
                "    }\n" +
                "}");
        list.add(service);
        return list;
    }

    public ServicePO getService(String appName, String serviceName) {
        ServicePO service = new ServicePO();
        service.setAppId("test-app");
        service.setName("TestUser");
        service.setScript("package olcp.test;\n" +
                "\n" +
                "class User {\n" +
                "    def getUserInfo() {\n" +
                "        def info = [:]\n" +
                "        info.put(\"id\", \"1\");\n" +
                "        info.put(\"name\", \"test\");\n" +
                "        return info\n" +
                "    }\n" +
                "}");
        return service;
    }
}
