package top.jiangliuhong.olcp.common.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * custom sql executor
 */
public class SqlExecutor {

    @PersistenceContext
    private EntityManager entityManager;

    public int executeDDL(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    public <T> List<T> executeQuery(String sql, Map<String, Object> parameters, Class<T> resultClass) {
        TypedQuery<T> query = entityManager.createQuery(sql, resultClass);
        if (parameters != null && !parameters.isEmpty()) {
            parameters.forEach(query::setParameter);
        }
        return query.getResultList();
    }

}
