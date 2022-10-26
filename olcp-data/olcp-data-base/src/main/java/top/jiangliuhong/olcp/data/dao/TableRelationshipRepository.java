package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.data.bean.TableRelationshipDO;

@Repository
public interface TableRelationshipRepository extends CrudRepository<TableRelationshipDO, String> {
}
