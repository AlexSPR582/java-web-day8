package com.alexander.day8.model;

import com.alexander.day8.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    public static ConnectionCreator connectionCreator;

    private static final Properties PROPERTIES;
    private static final String URL = "jdbc:mysql://localhost:3306/library";

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", "root");
        PROPERTIES.put("password", "1234");
        PROPERTIES.put("verifyServerCertificate", "false");
        PROPERTIES.put("useSSL", "false");
        PROPERTIES.put("requireSSL", "false");
        PROPERTIES.put("useLegacyDatetimeCode", "false");
        PROPERTIES.put("serverTimezone", "UTC");
    }

    public static ConnectionCreator getInstance() {
        if (connectionCreator == null) {
            connectionCreator = new ConnectionCreator();
        }
        return connectionCreator;
    }

    private ConnectionCreator() {}

    public Connection createConnection() throws DaoException {
        Connection cn;
        try {
            cn = DriverManager.getConnection(URL, PROPERTIES);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return cn;
    }
}
