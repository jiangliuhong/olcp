package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;

import java.util.List;

@Repository
public interface TableFieldRepository extends PagingAndSortingRepository<TableFieldDO, String> {


    /**
     * 根据数据表ID查询
     *
     * @return 不为系统默认字段的列表
     */
    public List<TableFieldDO> findAllByTableIdAndSystemFieldIsTrue(String tableId);
}
