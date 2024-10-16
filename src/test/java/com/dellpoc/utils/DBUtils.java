package com.dellpoc.utils;

import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

public class DBUtils {

    private static Connection connection;
    private static final Logger log = Logger.getLogger(DBUtils.class);

    public static void connectToDB(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static Connection MemSQLconnect(String host, String dbName, String user, String pass) throws Exception {
        String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            if (!conn.isClosed()) {
                log.info("Connected to MemSQL Host " + url);
            }
        } catch (NullPointerException | SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }

        return conn;
    }

    public static Connection OracleConnect(String host, String serviceName, String user, String pass) throws Exception {
        String url = "jdbc:oracle:thin:@//" + host + ":1521/" + serviceName;
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }

        try {
            connection = DriverManager.getConnection(url, user, pass);
            if (!connection.isClosed()) {
                log.info("Connected to Oracle Host " + host);
            }
        } catch (NullPointerException | SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }

        return connection;
    }

    public static ResultSet executeQuery(Connection conn, String queryString) throws Exception {
        ResultSet resultSet = null;

        try {
            Statement stm = conn.createStatement();
            resultSet = stm.executeQuery(queryString);
            if (!resultSet.isBeforeFirst()) {
                log.info("ResultSet is empty");
            }
        } catch (NullPointerException | SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }

        return resultSet;
    }

    public static String getStringVal(Connection conn, String queryString) throws Exception {
        String value = "";
        try {
            ResultSet resultSet = DBUtils.executeQuery(conn, queryString);
            if (resultSet.next()) {
                value = resultSet.getString(1);
            } else {
                log.info("ResultSet is null");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return value;
    }

    public static List<String> getListVal(Connection conn, String queryString) throws Exception {
        List<String> value = new ArrayList<>();
        try {
            ResultSet resultSet = DBUtils.executeQuery(conn, queryString);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    value.add(resultSet.getString(i));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return value;
    }

    public static String getStatusCodeVal(Connection conn, String queryString) throws Exception {
        String value = null;
        try {
            ResultSet resultSet = DBUtils.executeQuery(conn, queryString);
            if (resultSet.next()) {
                value = Integer.toString(resultSet.getInt(1));
            } else {
                log.info("ResultSet is null");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return value;
    }

    public static int countQuery(Connection conn, String queryString) throws Exception {
        int value = 0;
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(queryString);
            while (resultSet.next()) {
                value = Integer.parseInt(resultSet.getString(1));
            }
        } catch (NullPointerException | SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }
        return value;
    }

    public int executeNonQuery(String queryString) throws Exception {
        int result = 0;
        try {
            Statement stm = connection.createStatement();
            result = stm.executeUpdate(queryString);
        } catch (NullPointerException | SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }
        return result;
    }

    public static List<HashMap<String, Object>> resultSetToArrayList(ResultSet resultSet) throws Exception {
        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), resultSet.getObject(i));
                }
                list.add(row);
            }
        } catch (NullPointerException | SQLException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }
        return list;
    }

    public static void teardownConnection(Connection conn) throws Exception {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("SQLException has been raised.", e);
            throw e;
        } catch (Exception e) {
            log.error("Generic Exception has been raised.", e);
            throw e;
        }
    }

    // ETL Testing Validation Utilities

    /**
     * Validates that the row count between source and target tables matches.
     */
    public static boolean validateRowCount(Connection sourceConn, String sourceQuery, Connection targetConn, String targetQuery) throws Exception {
        int sourceCount = countQuery(sourceConn, sourceQuery);
        int targetCount = countQuery(targetConn, targetQuery);
        return sourceCount == targetCount;
    }

    /**
     * Validates that data between source and target tables matches.
     */
    public static boolean validateDataIntegrity(Connection sourceConn, String sourceQuery, Connection targetConn, String targetQuery) throws Exception {
        ResultSet sourceResultSet = executeQuery(sourceConn, sourceQuery);
        ResultSet targetResultSet = executeQuery(targetConn, targetQuery);

        List<HashMap<String, Object>> sourceData = resultSetToArrayList(sourceResultSet);
        List<HashMap<String, Object>> targetData = resultSetToArrayList(targetResultSet);

        return sourceData.equals(targetData);
    }

    /**
     * Validates that the schema (column names and types) between source and target tables matches.
     */
    public static boolean validateSchema(Connection sourceConn, String sourceQuery, Connection targetConn, String targetQuery) throws Exception {
        ResultSet sourceResultSet = executeQuery(sourceConn, sourceQuery);
        ResultSet targetResultSet = executeQuery(targetConn, targetQuery);

        ResultSetMetaData sourceMetaData = sourceResultSet.getMetaData();
        ResultSetMetaData targetMetaData = targetResultSet.getMetaData();

        if (sourceMetaData.getColumnCount() != targetMetaData.getColumnCount()) {
            return false;
        }

        for (int i = 1; i <= sourceMetaData.getColumnCount(); i++) {
            if (!sourceMetaData.getColumnName(i).equals(targetMetaData.getColumnName(i)) ||
                    sourceMetaData.getColumnType(i) != targetMetaData.getColumnType(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates that there are no unexpected null values in critical columns.
     */
    public static boolean validateNullValues(Connection conn, String query, List<String> criticalColumns) throws Exception {
        ResultSet resultSet = executeQuery(conn, query);

        while (resultSet.next()) {
            for (String column : criticalColumns) {
                if (resultSet.getObject(column) == null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Validates that data types between source and target tables match.
     */
    public static boolean validateDataTypes(Connection sourceConn, String sourceQuery, Connection targetConn, String targetQuery) throws Exception {
        ResultSet sourceResultSet = executeQuery(sourceConn, sourceQuery);
        ResultSet targetResultSet = executeQuery(targetConn, targetQuery);

        ResultSetMetaData sourceMetaData = sourceResultSet.getMetaData();
        ResultSetMetaData targetMetaData = targetResultSet.getMetaData();

        for (int i = 1; i <= sourceMetaData.getColumnCount(); i++) {
            if (sourceMetaData.getColumnType(i) != targetMetaData.getColumnType(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates that there are no duplicate rows in the target table.
     */
    public static boolean validateNoDuplicates(Connection conn, String query, List<String> keyColumns) throws Exception {
        ResultSet resultSet = executeQuery(conn, query);

        HashMap<String, Integer> rowMap = new HashMap<>();
        while (resultSet.next()) {
            StringBuilder key = new StringBuilder();
            for (String column : keyColumns) {
                key.append(resultSet.getString(column)).append("|");
            }
            String keyStr = key.toString();
            rowMap.put(keyStr, rowMap.getOrDefault(keyStr, 0) + 1);
        }

        for (int count : rowMap.values()) {
            if (count > 1) {
                return false;
            }
        }

        return true;
    }
}
