package com.link_intersystems.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallable<T> {
    T call(Connection connection) throws SQLException;
}
