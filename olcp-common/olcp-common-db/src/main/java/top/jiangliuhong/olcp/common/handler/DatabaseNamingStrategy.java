package top.jiangliuhong.olcp.common.handler;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import top.jiangliuhong.olcp.common.utils.NameUtils;

public class DatabaseNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final long serialVersionUID = 1383021413247872469L;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        // 将表名全部转换成大写
        //String tableName = name.getText().toUpperCase();
        return Identifier.toIdentifier(name.getText());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return Identifier.toIdentifier(NameUtils.camelToUnderline(name.getText()));
    }
}
