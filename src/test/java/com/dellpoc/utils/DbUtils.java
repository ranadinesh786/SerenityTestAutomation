package com.dellpoc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtils {
    private static final Logger log = LoggerFactory.getLogger(DbUtils.class);

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "username", "password");
            log.info("Database connection established");
            Serenity.recordReportData().withTitle("Database Connection").andContents("Database connection established");
            return connection;
        } catch (Exception e) {
            log.error("Error establishing database connection", e);
            Serenity.recordReportData().withTitle("Database Connection Error").andContents("Error establishing database connection: " + e.getMessage());
            return null;
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            log.info("Executed query: " + query);
            Serenity.recordReportData().withTitle("Database Query Execution").andContents("Executed query: " + query);
            return resultSet;
        } catch (Exception e) {
            log.error("Error executing query: " + query, e);
            Serenity.recordReportData().withTitle("Database Query Error").andContents("Error executing query: " + query + " - " + e.getMessage());
            return null;
        }
    }
}
