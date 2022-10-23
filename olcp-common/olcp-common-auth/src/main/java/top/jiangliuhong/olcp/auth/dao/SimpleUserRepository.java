package top.jiangliuhong.olcp.auth.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.auth.bean.SimpleUserDO;

@Repository
public interface SimpleUserRepository extends CrudRepository<SimpleUserDO, String> {

    SimpleUserDO findByUsername(String username);
}
