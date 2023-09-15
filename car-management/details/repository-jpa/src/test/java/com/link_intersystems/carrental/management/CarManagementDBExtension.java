package com.link_intersystems.carrental.management;

import com.link_intersystems.jdbc.test.db.h2.H2Config;
import com.link_intersystems.jdbc.test.db.h2.H2Extension;
import com.link_intersystems.jdbc.test.db.setup.DBSetup;
import com.link_intersystems.sql.io.InputStreamScriptResource;
import com.link_intersystems.sql.io.SqlScript;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author René Link {@literal <rene.link@link-intersystems.com>}
 */
@ExtendWith(H2Extension.class)
@H2Config(databaseSetup = CarManagementDBExtension.CarManagementDBSetup.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CarManagementDBExtension {
    class CarManagementDBSetup implements DBSetup {
        @Override
        public void setupSchema(Connection connection) throws SQLException {
            InputStream resourceAsStream = CarManagementDBSetup.class.getResourceAsStream("/com/link_intersystems/carrental/management/init.sql");
            SqlScript sqlScript = new SqlScript(new InputStreamScriptResource(resourceAsStream));
            sqlScript.execute(connection);
        }

        @Override
        public void setupDdl(Connection connection) throws SQLException {
        }

        @Override
        public void setupData(Connection connection) throws SQLException {

        }
    }
}