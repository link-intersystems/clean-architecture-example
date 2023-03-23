package com.link_intersystems.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.*;

public class JdbcTemplate {

    public static final Object[] EMPTY_ARGS = new Object[0];
    private DataSource dataSource;
    private String schema;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = requireNonNull(dataSource);
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        return query(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                PreparedStatementCallable<List<Map<String, Object>>> preparedStatementCallable = callForObjects(args, new MapRowMapper());
                return preparedStatementCallable.call(ps);
            }
        });
    }

    public Map<String, Object> queryForMap(String sql, Object... args) {
        return query(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                PreparedStatementCallable<Map<String, Object>> preparedStatementCallable = callForObject(args, new MapRowMapper());
                return preparedStatementCallable.call(ps);
            }
        });
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        return query(sql, EMPTY_ARGS, rowMapper);
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        return query(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                PreparedStatementCallable<List<T>> preparedStatementCallable = callForObjects(args, rowMapper);
                return preparedStatementCallable.call(ps);
            }
        });
    }

    private <T> PreparedStatementCallable<List<T>> callForObjects(Object[] args, RowMapper<T> rowMapper) {
        return ps -> {
            for (int paramIndex = 1; paramIndex <= args.length; paramIndex++) {
                ps.setObject(paramIndex, args[paramIndex - 1]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<T> rowElements = new ArrayList<>();

                while (rs.next()) {
                    T rowElement = rowMapper.mapRow(rs);
                    rowElements.add(rowElement);
                }

                return rowElements;
            }
        };
    }


    public <T> T query(ConnectionCallable<T> connectionCallable) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setSchema(schema);
            return connectionCallable.call(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
        return queryForObject(sql, EMPTY_ARGS, rowMapper);
    }

    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
        return query(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                PreparedStatementCallable<T> preparedStatementCallable = callForObject(args, rowMapper);
                return preparedStatementCallable.call(ps);
            }
        });
    }

    private <T> PreparedStatementCallable<T> callForObject(Object[] args, RowMapper<T> rowMapper) {
        return ps -> {
            for (int paramIndex = 1; paramIndex <= args.length; paramIndex++) {
                ps.setObject(paramIndex, args[paramIndex - 1]);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rowMapper.mapRow(rs);
            }

            return null;
        };
    }

    public int update(ConnectionCallable<Integer> connectionCallable) {
        return query(connectionCallable);
    }

    public int update(String sql, Object... args) {
        return query(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);

            for (int paramIndex = 1; paramIndex <= args.length; paramIndex++) {
                ps.setObject(paramIndex, args[paramIndex - 1]);
            }

            return ps.executeUpdate();
        });
    }


}
