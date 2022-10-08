package top.jiangliuhong.olcp.engine.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * list / table api
 */
@RestController
@RequestMapping("/api/v1/list/{id}")
public class ListApi {

    @RequestMapping("/config")
    public Object config(@PathVariable("id") String listId) {
        return null;
    }

    @RequestMapping("/data")
    public Object data(@PathVariable("id") String listId) {
        return null;
    }

    @RequestMapping("/data/{recordId}")
    public Object oneData(@PathVariable("id") String listId, @PathVariable("recordId") String recordId) {
        return null;
    }

}
