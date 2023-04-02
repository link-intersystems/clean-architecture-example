package com.link_intersystems.jdbc;

import com.link_intersystems.jdbc.test.db.sakila.SakilaSlimExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SakilaSlimExtension
class MapRowMapperTest {

    private Statement statement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp(Connection connection) throws SQLException {
        statement = connection.createStatement();
        statement.execute("select * from actor where actor_id in (1,2)");
        resultSet = statement.getResultSet();
    }

    @AfterEach
    void tearDown() throws SQLException {
        resultSet.close();
        statement.close();
    }

    @Test
    void mapRow() throws SQLException {
        MapRowMapper mapRowMapper = new MapRowMapper();

        resultSet.next();

        Map<String, Object> row = mapRowMapper.mapRow(resultSet);

        assertEquals(1, row.get("ACTOR_ID"));
        assertEquals("PENELOPE", row.get("FIRST_NAME"));
        assertEquals("GUINESS", row.get("LAST_NAME"));
        assertEquals(new Timestamp(106, 1, 15, 4, 34, 33, 0), row.get("LAST_UPDATE"));
    }
}