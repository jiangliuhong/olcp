package top.jiangliuhong.olcp.auth.dao;

import org.springframework.data.repository.CrudRepository;
import top.jiangliuhong.olcp.auth.bean.UserDO;

public interface UserRepository extends CrudRepository<UserDO, String> {

    UserDO findByUsername(String username);
}
