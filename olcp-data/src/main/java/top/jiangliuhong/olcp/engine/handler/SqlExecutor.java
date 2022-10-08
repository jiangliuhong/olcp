package top.jiangliuhong.olcp.engine.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Use SqlSession execute sql script
 */
@Component
@Slf4j
public class SqlExecutor {

    private final SqlSessionTemplate sqlSessionTemplate;

    public SqlExecutor(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    /**
     * get sql session
     *
     * @return SqlSession
     */
    public SqlSession getSqlSession() {
        return SqlSessionUtils.getSqlSession(
                this.sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(),
                sqlSessionTemplate.getPersistenceExceptionTranslator()
        );
    }

    /**
     * close sql session
     *
     * @param session session
     */
    public void closeSqlSession(SqlSession session) {
        SqlSessionUtils.closeSqlSession(session, sqlSessionTemplate.getSqlSessionFactory());
    }

    /**
     * execute sql
     *
     * @param sql sql
     * @return sql execution result
     */
    public boolean executeSql(String sql) {
        SqlSession session = this.getSqlSession();
        PreparedStatement pst = null;
        try {
            pst = session.getConnection().prepareStatement(sql);
            return pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            this.closeSqlSession(session);
        }
    }
}