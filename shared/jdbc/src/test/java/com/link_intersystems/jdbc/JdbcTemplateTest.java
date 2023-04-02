package com.link_intersystems.jdbc;

import com.link_intersystems.jdbc.test.db.sakila.SakilaSlimExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SakilaSlimExtension
class JdbcTemplateTest {

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
    }

    @Test
    void queryForList() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM actor WHERE actor_id in (?,?)", 1, 2);

        assertEquals(2, list.size());

        assertEquals(1, list.get(0).get("ACTOR_ID"));
        assertEquals(2, list.get(1).get("ACTOR_ID"));
    }

    @Test
    void queryForMap() {
        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM actor WHERE actor_id = ?", 1);

        assertNotNull(row);
        assertEquals(1, row.get("ACTOR_ID"));
    }

    @Test
    void query() {
        List<Integer> count = jdbcTemplate.query("SELECT count(*) FROM actor WHERE actor_id in (?,?)", new Object[]{1, 2}, rs -> rs.getInt(1));

        assertEquals(1, count.size());
        assertEquals(2, count.get(0));
    }

    @Test
    void queryForObject() {
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM actor WHERE actor_id in (?,?)", new Object[]{1, 2}, rs -> rs.getInt(1));

        assertEquals(2, count);
    }

    @Test
    void update() {
        int updated = jdbcTemplate.update("insert into actor (ACTOR_ID, FIRST_NAME, LAST_NAME, LAST_UPDATE) VALUES(?, ?, ?, ?)", 1000, "René", "Link", new Timestamp(System.currentTimeMillis()));

        assertEquals(1, updated);
    }

    @Test
    void testUpdate() {
    }
}