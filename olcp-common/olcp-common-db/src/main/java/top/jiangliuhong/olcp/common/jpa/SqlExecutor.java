package top.jiangliuhong.olcp.common.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jiangliuhong.olcp.common.exception.SqlExecuteException;
import top.jiangliuhong.olcp.common.utils.ExceptionUtils;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @org.springframework.data.jpa.repository.Query
    public int executeUpdate(String sql, Map<String, Object> parameters) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            if (parameters != null && !parameters.isEmpty()) {
                parameters.forEach(query::setParameter);
            }
            return query.executeUpdate();
        } catch (Exception e) {
            throw new SqlExecuteException("执行sql异常:" + ExceptionUtils.getExceptionMessage(e), e);
        }
    }

    public int executeUpdate(String sql, List<Object> parameters) {
        Query query = entityManager.createNativeQuery(sql);
        if (parameters != null && !parameters.isEmpty()) {
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i, parameters.get(i));
            }
        }
        return query.executeUpdate();
    }

}
