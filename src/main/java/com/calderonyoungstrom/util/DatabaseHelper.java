package com.calderonyoungstrom.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created on 12/4/16.
 */
public class DatabaseHelper {
    // Update this with your db username
    private static final String DB_USERNAME = "root";
    // Update this with your db password
    private static final String DB_PASSWORD = "Pass1234";
    // Update this with your db URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/footballProject?autoReconnect=true&useSSL=false";

    public static Connection loginToDB() throws ClassNotFoundException, SQLException,
            IllegalAccessException, InstantiationException {
        return loginToDB(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private static Connection loginToDB(String dbUrl, String username, String password) throws ClassNotFoundException, SQLException,
            IllegalAccessException, InstantiationException {
        //Connect to DB with credentials

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
