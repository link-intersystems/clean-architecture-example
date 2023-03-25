package com.link_intersystems.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.*;

public class MapRowMapper implements RowMapper<Map<String, Object>> {

    private Supplier<Map<String, Object>> mapFactory;
    private Function<String, String> effectiveColumnNameFunc = String::toUpperCase;

    public MapRowMapper() {
        this(LinkedHashMap::new);
    }

    public MapRowMapper(Supplier<Map<String, Object>> mapFactory) {
        this.mapFactory = requireNonNull(mapFactory);
    }

    @Override
    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
        Map<String, Object> row = mapFactory.get();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            Object columnValue = rs.getObject(columnIndex);
            String columnName = metaData.getColumnName(columnIndex);
            String effectiveColumnName = effectiveColumnNameFunc.apply(columnName);
            row.put(effectiveColumnName, columnValue);
        }

        return row;
    }

}
