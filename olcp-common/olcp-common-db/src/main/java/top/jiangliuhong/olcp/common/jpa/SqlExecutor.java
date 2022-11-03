package top.jiangliuhong.olcp.common.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public int executeUpdate(String sql, Map<String, Object> parameters) {
        Query query = entityManager.createQuery(sql);
        if (parameters != null && !parameters.isEmpty()) {
            parameters.forEach(query::setParameter);
        }
        return query.executeUpdate();
    }

    public int executeUpdate(String sql, List<Object> parameters) {
        Query query = entityManager.createQuery(sql);
        if (parameters != null && !parameters.isEmpty()) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i, parameters.get(i));
            }
        }
        return query.executeUpdate();
    }

}
