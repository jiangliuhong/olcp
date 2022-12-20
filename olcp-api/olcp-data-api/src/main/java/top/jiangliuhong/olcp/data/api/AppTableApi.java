package top.jiangliuhong.olcp.data.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jiangliuhong.olcp.common.bean.PageInfo;
import top.jiangliuhong.olcp.common.utils.BeanUtils;
import top.jiangliuhong.olcp.data.bean.AppTableQuery;
import top.jiangliuhong.olcp.data.bean.SortData;
import top.jiangliuhong.olcp.data.bean.TablePageQueryParam;
import top.jiangliuhong.olcp.data.bean.TableVO;
import top.jiangliuhong.olcp.data.bean.po.TablePO;
import top.jiangliuhong.olcp.data.consts.Sorts;
import top.jiangliuhong.olcp.data.entity.EntityValue;
import top.jiangliuhong.olcp.data.exception.TableNotFoundException;
import top.jiangliuhong.olcp.data.service.AppTableService;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "appTable", description = "应用数据表API")
@RestController
@RequestMapping("/api/v1/data/table/{appName}/{tableName}")
public class AppTableApi {

    @Autowired
    private AppTableService appTableService;

    @GetMapping("/struct")
    @Operation(summary = "查询数据表结构")
    public TableVO queryTableStruct(@PathVariable String appName,
                                    @PathVariable String tableName) {
        TablePO table = appTableService.getTable(appName, tableName);
        if (table == null) {
            throw new TableNotFoundException("没有找到表:" + appName + "." + tableName);
        }
        TableVO vo = new TableVO();
        BeanUtils.copyProperties(table, vo);
        return vo;
    }

    @PostMapping("/data")
    @Operation(summary = "分页查询应用表数据")
    public PageInfo<EntityValue> queryPage(@PathVariable String appName,
                                           @PathVariable String tableName,
                                           @RequestBody AppTableQuery query) {
        TablePageQueryParam param = new TablePageQueryParam();
        param.setPage(query.getPage());
        param.setSize(query.getSize());
        List<String> orderBy = query.getOrderBy();
        if (orderBy != null && !orderBy.isEmpty()) {
            if (param.getOrders() == null) {
                param.setOrders(new ArrayList<>());
            }
            orderBy.forEach(names -> {
                String[] split = names.split(",");
                String type = "";
                if (split.length == 2) {
                    type = split[1];
                }
                param.getOrders().add(new SortData(split[0], Sorts.getSortsByName(type)));
            });
        }
        return appTableService.queryTableDate(appName, tableName, param);
    }

}
