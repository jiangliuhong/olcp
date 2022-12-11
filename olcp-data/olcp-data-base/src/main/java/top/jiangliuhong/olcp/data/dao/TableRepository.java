package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import top.jiangliuhong.olcp.data.bean.TableDO;

import java.util.List;

@Repository
public interface TableRepository extends PagingAndSortingRepository<TableDO, String> {

    /**
     * 查询数据表
     *
     * @param name  表名
     * @param appId 应用ID
     * @return 数据表
     */
    public TableDO findByNameAndAppId(String name, String appId);

    /**
     * 查询一个应用下所有表
     *
     * @param appId 应用ID
     * @return 数据表
     */
    public List<TableDO> findAllByAppId(String appId);

    /**
     * 查询多个应用下所有表
     *
     * @param appIds 应用ID数组
     * @return 数据表
     */
    public List<TableDO> findAllByAppIdIn(String[] appIds);

    public Page<TableDO> findAll(Specification<TableDO> spec, Pageable pageable);

}
