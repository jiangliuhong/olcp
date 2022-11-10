package top.jiangliuhong.olcp.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.data.bean.dto.TableDTO;
import top.jiangliuhong.olcp.data.sql.DatabaseMetaData;

import javax.transaction.Transactional;

/**
 * 系统元数据服务
 */
@Service
public class DatabaseMetaDataService {


    @Autowired
    private DatabaseMetaData databaseMetaData;
    @Autowired
    private TableService tableService;

    @Transactional
    public String addTable(TableDTO table) {
        String tableId = tableService.addTable(table);
        return tableId;
    }

    public void updateTable(TableDTO table) {
        tableService.updateTable(table);
    }

}
