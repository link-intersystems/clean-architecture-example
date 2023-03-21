package com.link_intersystems.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCallable<T> {
    T call(PreparedStatement preparedStatement) throws SQLException;
}
