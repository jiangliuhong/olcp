package top.jiangliuhong.olcp.data.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.TableVO;
import top.jiangliuhong.olcp.data.bean.dto.TableDTO;
import top.jiangliuhong.olcp.data.service.DatabaseMetaDataService;
import top.jiangliuhong.olcp.data.service.TableService;

@Tag(name = "table", description = "数据表API")
@RestController
@RequestMapping("/api/v1/data/table")
public class TableApi {

    @Autowired
    private TableService tableService;

    @Autowired
    private DatabaseMetaDataService databaseMetaDataService;

    @PostMapping
    @Operation(summary = "新增数据表")
    public String addTable(@RequestBody TableVO tableVO) {
        return databaseMetaDataService.addTable(tableVO);
    }

    @PutMapping
    @Operation(summary = "修改数据表")
    public void updateTable(@RequestBody TableVO tableVO) {
        databaseMetaDataService.updateTable(tableVO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询数据表")
    public TableVO getTable(@PathVariable String id) {
        TableDTO tableInfo = tableService.getTableInfo(id);
        if (tableInfo == null) {
            return null;
        }
        TableVO table = new TableVO();
        BeanUtils.copyProperties(tableInfo, table);
        return table;
    }

}
