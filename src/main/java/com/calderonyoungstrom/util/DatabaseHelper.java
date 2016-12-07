package com.calderonyoungstrom.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created on 12/4/16.
 */
public class DatabaseHelper {
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Pass1234";

    public static Connection loginToDB() throws ClassNotFoundException, SQLException,
            IllegalAccessException, InstantiationException {
       return loginToDB(DB_USERNAME, DB_PASSWORD);
    }

    public static Connection loginToDB(String username, String password) throws ClassNotFoundException, SQLException,
            IllegalAccessException, InstantiationException {
        //Connect to DB with credentials
        String url = "jdbc:mysql://localhost:3306/footballProject?autoReconnect=true&useSSL=false";

        return DriverManager.getConnection(url, username, password);
    }
}
