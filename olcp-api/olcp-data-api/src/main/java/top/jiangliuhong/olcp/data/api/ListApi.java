package top.jiangliuhong.olcp.data.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data/list")
public class ListApi {

    @GetMapping("/{id}/config")
    public String config(@PathVariable String id) {
        return id;
    }


}
