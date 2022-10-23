package top.jiangliuhong.olcp.auth.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.auth.bean.UserDO;

@Repository
public interface UserRepository extends CrudRepository<UserDO, String> {

    UserDO findByUsername(String username);
}
