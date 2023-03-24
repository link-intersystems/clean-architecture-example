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
import java.text.MessageFormat;

public class DataSourceConfig {

    public static final String DEFAULT_JDBC_URL_TEMPLATE = "jdbc:h2:file:{0};USER=sa;PASSWORD=123";

    private JdbcDataSource dataSource;
    private JdbcTemplate bookingJdbcTemplate;
    private JdbcTemplate managementJdbcTemplate;

    private AppArgs appArgs;

    public DataSourceConfig(AppArgs appArgs) {
        this.appArgs = appArgs;
    }


    public DataSource getDataSource() {
        if (dataSource == null) {
            String dbPath = getDBPath();
            String jdbcUrl = MessageFormat.format(DEFAULT_JDBC_URL_TEMPLATE, dbPath);
            dataSource = createDataSource(jdbcUrl);
            ensureDatabaseInitialized(dataSource);
        }

        return dataSource;
    }

    private String getDBPath() {
        String dbName = appArgs.getArg("db", "./carrental");
        if (dbName.equals(".mv.db")) {
            dbName = dbName.substring(0, dbName.length() - ".mv.db".length());
        }
        return dbName;
    }

    private void ensureDatabaseInitialized(DataSource dataSource) {
        String dbPath = getDBPath();
        String dbFilePath = MessageFormat.format("./{0}.mv.db", dbPath);
        if (!new File(dbFilePath).exists()) {
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
            URL scriptResourceURL = DataSourceConfig.class.getResource(scriptResource);
            SqlScript sqlScript = new SqlScript(new URLScriptResource(scriptResourceURL));
//            sqlScript.execute(System.out::println);
            sqlScript.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JdbcTemplate getCarRentalJdbcTemplate() {
        if (bookingJdbcTemplate == null) {
            DataSource dataSource = getDataSource();
            bookingJdbcTemplate = new JdbcTemplate(dataSource, "BOOKING");
        }

        return bookingJdbcTemplate;
    }

    public JdbcTemplate getManagementJdbcTemplate() {
        if (managementJdbcTemplate == null) {
            DataSource dataSource = getDataSource();
            managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        }

        return managementJdbcTemplate;
    }
}
