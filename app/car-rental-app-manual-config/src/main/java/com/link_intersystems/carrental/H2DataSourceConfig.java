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

    public DataSource getCarRentalDataSource() {
        ensureDatabaseInitialized();

        return createDataSource(DEFAULT_JDBC_URL + ";SCHEMA=BOOKING");
    }

    private void ensureDatabaseInitialized() {
        if (!new File("./carrental.mv.db").exists()) {
            JdbcDataSource jdbcDataSource = createDataSource(DEFAULT_JDBC_URL);

            executeScript(jdbcDataSource, "/com/link_intersystems/carrental/init.sql");
            executeScript(jdbcDataSource, "/com/link_intersystems/carrental/management/init.sql");
        }
    }

    private static JdbcDataSource createDataSource(String jdbcUrl) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(jdbcUrl);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("123");
        return jdbcDataSource;
    }

    public DataSource getManagementDataSource() {
        ensureDatabaseInitialized();

        return createDataSource(DEFAULT_JDBC_URL + ";SCHEMA=MANAGEMENT");
    }

    private void executeScript(JdbcDataSource jdbcDataSource, String scriptResource) {
        try (Connection connection = jdbcDataSource.getConnection()) {
            URL scriptResourceURL = H2DataSourceConfig.class.getResource(scriptResource);
            SqlScript sqlScript = new SqlScript(new URLScriptResource(scriptResourceURL));
//            sqlScript.execute(System.out::println);
            sqlScript.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JdbcTemplate getCarRentalJdbcTemplate(DataSource carRentalDataSource) {
        return new JdbcTemplate(carRentalDataSource);
    }

    public JdbcTemplate getManagementJdbcTemplate(DataSource managementDataSource) {
        return new JdbcTemplate(managementDataSource);
    }
}
