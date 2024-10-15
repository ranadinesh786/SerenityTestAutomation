package com.dellpoc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtils {
    private static final String URL = "jdbc:oracle:thin:@testservername:1521:xe";
    private static final String USER = "testusername";
    private static final String PASSWORD = "Welcome@1";

    private static Connection connection;

    public static void connectToDatabase() throws Exception {
        // Load Oracle JDBC Driver
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet executeQuery(String query) throws Exception {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void closeConnection() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
