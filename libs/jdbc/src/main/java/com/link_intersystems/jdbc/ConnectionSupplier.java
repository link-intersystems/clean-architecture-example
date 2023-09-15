package com.link_intersystems.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionSupplier {
    public Connection get() throws SQLException;
}
