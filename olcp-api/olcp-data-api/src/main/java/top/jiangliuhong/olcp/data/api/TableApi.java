package top.jiangliuhong.olcp.data.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import top.jiangliuhong.olcp.data.bean.TableVO;
import top.jiangliuhong.olcp.data.service.TableService;

@Tag(name = "app", description = "应用程序API")
@RestController
@RequestMapping("/api/v1/data/table")
public class TableApi {

    @Autowired
    private TableService tableService;

    @PostMapping
    @Operation(summary = "新增数据表")
    public String addTable(@RequestBody TableVO tableVO) {
        return tableService.addTable(tableVO);
    }

    @PutMapping
    @Operation(summary = "修改数据表")
    public void updateTable(@RequestBody TableVO tableVO) {
        tableService.updateTable(tableVO);
    }

}
