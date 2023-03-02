package com.link_intersystems.carrental;

import com.link_intersystems.sql.io.InputStreamScriptResource;
import com.link_intersystems.sql.io.SqlScript;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class H2DataSourceConfig {

    public DataSource getDataSource() {
        StringBuilder urlBuilder = new StringBuilder("jdbc:h2:file:./car-rental");
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(urlBuilder.toString());
        jdbcDataSource.setUser("");
        jdbcDataSource.setPassword("");

        if (!new File("./car-rental.mv.db").exists()) {
            try (Connection connection = jdbcDataSource.getConnection()) {
                InputStream resourceAsStream = H2DataSourceConfig.class.getResourceAsStream("/com/link_intersystems/carrental/offer/init.sql");
                SqlScript sqlScript = new SqlScript(new InputStreamScriptResource(resourceAsStream));
                sqlScript.execute(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return jdbcDataSource;
    }

}
