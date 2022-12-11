package top.jiangliuhong.olcp.common.jpa;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jiangliuhong.olcp.common.exception.SqlExecuteException;
import top.jiangliuhong.olcp.common.utils.ExceptionUtils;
import top.jiangliuhong.olcp.common.utils.LiteStringMap;
import top.jiangliuhong.olcp.common.utils.StringObjectMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public int executeQueryCount(String sql, Object... parameters) {
        Query query = executeBaseQuery(sql, null, parameters);
        return ((Number) query.getSingleResult()).intValue();
    }

    public List<StringObjectMap> executeQuery(String sql, ResultTransformer resultSetMapping, Object... parameters) {
        Query query = executeBaseQuery(sql, resultSetMapping, parameters);
        return query.getResultList();
    }

    private Query executeBaseQuery(String sql, ResultTransformer resultSetMapping, Object[] parameters) {
        Query query = entityManager.createNativeQuery(sql);
        if (resultSetMapping != null) {
            query.unwrap(NativeQuery.class).setResultTransformer(resultSetMapping);
        }
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                query.setParameter(i + 1, parameter);
            }
        }
        return query;
    }

    public <T> List<T> executeQuery(String sql, Map<String, Object> parameters, Class<T> resultClass) {
        Query query = entityManager.createNativeQuery(sql, resultClass);
        if (parameters != null && !parameters.isEmpty()) {
            parameters.forEach(query::setParameter);
        }
        return query.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @org.springframework.data.jpa.repository.Query
    public int executeUpdate(String sql, LiteStringMap<Object> parameters) {
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @org.springframework.data.jpa.repository.Query
    public int executeUpdate(String sql, List<Object> parameters) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            if (parameters != null && !parameters.isEmpty()) {
                for (int i = 0; i < parameters.size(); i++) {
                    query.setParameter(i + 1, parameters.get(i));
                }
            }
            return query.executeUpdate();
        } catch (Exception e) {
            throw new SqlExecuteException("执行sql异常:" + ExceptionUtils.getExceptionMessage(e), e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @org.springframework.data.jpa.repository.Query
    public int executeUpdate(String sql, Object... parameters) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.executeUpdate();
        } catch (Exception e) {
            throw new SqlExecuteException("执行sql异常:" + ExceptionUtils.getExceptionMessage(e), e);
        }
    }

}
