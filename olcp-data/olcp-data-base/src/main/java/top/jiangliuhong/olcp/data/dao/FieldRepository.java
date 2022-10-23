package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.data.bean.FieldDO;

@Repository
public interface FieldRepository extends PagingAndSortingRepository<FieldDO, String> {

}
