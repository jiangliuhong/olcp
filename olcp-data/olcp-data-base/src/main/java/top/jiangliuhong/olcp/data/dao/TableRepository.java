package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.common.jpa.CommonSpecRepository;
import top.jiangliuhong.olcp.data.bean.TableDO;

@Repository
public interface TableRepository extends PagingAndSortingRepository<TableDO, String>, CommonSpecRepository<TableDO> {

}
