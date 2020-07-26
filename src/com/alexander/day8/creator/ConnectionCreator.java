package com.alexander.day8.creator;

import com.alexander.day8.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library?verifyServerCertificate=false&"
            + "useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public Connection create() throws DaoException {
        Connection cn;
        try {
            cn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return cn;
    }
}
