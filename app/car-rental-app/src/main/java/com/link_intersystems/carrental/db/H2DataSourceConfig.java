package com.link_intersystems.carrental.db;

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

    public DataSource dataSource() {
        JdbcDataSource jdbcDataSource = dataSource(DEFAULT_JDBC_URL);
        ensureDatabaseInitialized(jdbcDataSource);

        return dataSource(DEFAULT_JDBC_URL );
    }

    private void ensureDatabaseInitialized(JdbcDataSource jdbcDataSource) {
        if (!new File("./carrental.mv.db").exists()) {
            executeScript(jdbcDataSource, "/com/link_intersystems/carrental/init.sql");
            executeScript(jdbcDataSource, "/com/link_intersystems/carrental/management/init.sql");
        }
    }

    private static JdbcDataSource dataSource(String jdbcUrl) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(jdbcUrl);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("123");
        return jdbcDataSource;
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

    public JdbcTemplate carRentalJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setSchema("BOOKING");
        return jdbcTemplate;
    }

    public JdbcTemplate managementJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setSchema("MANAGEMENT");
        return jdbcTemplate;
    }
}
