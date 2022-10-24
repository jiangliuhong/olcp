package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.data.bean.AppDO;
import top.jiangliuhong.olcp.data.bean.ListFieldDO;

@Repository
public interface ListFieldRepository extends PagingAndSortingRepository<ListFieldDO, String> {

}
