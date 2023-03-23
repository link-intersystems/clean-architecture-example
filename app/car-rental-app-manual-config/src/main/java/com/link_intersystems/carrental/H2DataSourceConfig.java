package com.link_intersystems.carrental;

import com.link_intersystems.jdbc.JdbcTemplate;
import com.link_intersystems.sql.io.SqlScript;
import com.link_intersystems.sql.io.URLScriptResource;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class H2DataSourceConfig {

    public static final String DEFAULT_JDBC_URL = "jdbc:h2:file:./carrental;USER=sa;PASSWORD=123";

    public DataSource getDataSource() {
        JdbcDataSource dataSource = createDataSource(DEFAULT_JDBC_URL);
        ensureDatabaseInitialized(dataSource);
        return dataSource;
    }

    private void ensureDatabaseInitialized(DataSource dataSource) {
        if (!new File("./carrental.mv.db").exists()) {
            executeScript(dataSource, "/com/link_intersystems/carrental/init.sql");
            executeScript(dataSource, "/com/link_intersystems/carrental/management/init.sql");
        }
    }

    private static JdbcDataSource createDataSource(String jdbcUrl) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(jdbcUrl);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("123");
        return jdbcDataSource;
    }

    private void executeScript(DataSource dataSource, String scriptResource) {
        try (Connection connection = dataSource.getConnection()) {
            URL scriptResourceURL = H2DataSourceConfig.class.getResource(scriptResource);
            SqlScript sqlScript = new SqlScript(new URLScriptResource(scriptResourceURL));
//            sqlScript.execute(System.out::println);
            sqlScript.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JdbcTemplate getCarRentalJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource, "BOOKING");
    }

    public JdbcTemplate getManagementJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource, "MANAGEMENT");
    }
}
