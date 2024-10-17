package com.dellpoc.utils;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static Connection connection;
    private static final Logger log = Logger.getLogger(DBUtils.class);

    /**
     * Connects to the database using the provided URL, username, and password.
     *
     * @param url      The database URL.
     * @param user     The database username.
     * @param password The database password.
     * @throws SQLException If a database access error occurs.
     */
    public static void connectToDB(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Executes a SQL query and returns the result set.
     *
     * @param query The SQL query to execute.
     * @return The result set of the query.
     * @throws SQLException If a database access error occurs.
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Closes the database connection.
     *
     * @throws SQLException If a database access error occurs.
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Connects to a MemSQL database.
     *
     * @param host   The database host.
     * @param dbName The database name.
     * @param user   The database username.
     * @param pass   The database password.
     * @return The database connection.
     * @throws Exception If an error occurs while connecting to the database.
     */
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

    /**
     * Connects to an Oracle database.
     *
     * @param host        The database host.
     * @param serviceName The database service name.
     * @param user        The database username.
     * @param pass        The database password.
     * @return The database connection.
     * @throws Exception If an error occurs while connecting to the database.
     */
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

    /**
     * Connects to an Azure SQL database.
     *
     * @param host   The database host.
     * @param dbName The database name.
     * @param user   The database username.
     * @param pass   The database password.
     * @return The database connection.
     * @throws Exception If an error occurs while connecting to the database.
     */
    public static Connection AzureSQLConnect(String host, String dbName, String user, String pass) throws Exception {
        String url = "jdbc:sqlserver://" + host + ":1433;database=" + dbName + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            if (!conn.isClosed()) {
                log.info("Connected to Azure SQL Host " + url);
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

    /**
     * Connects to an AWS RDS database.
     *
     * @param host   The database host.
     * @param dbName The database name.
     * @param user   The database username.
     * @param pass   The database password.
     * @return The database connection.
     * @throws Exception If an error occurs while connecting to the database.
     */
    public static Connection AWSRDSConnect(String host, String dbName, String user, String pass) throws Exception {
        String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?useSSL=false";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            if (!conn.isClosed()) {
                log.info("Connected to AWS RDS Host " + url);
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

    /**
     * Connects to a Google Cloud SQL database.
     *
     * @param host   The database host.
     * @param dbName The database name.
     * @param user   The database username.
     * @param pass   The database password.
     * @return The database connection.
     * @throws Exception If an error occurs while connecting to the database.
     */
    public static Connection GoogleCloudSQLConnect(String host, String dbName, String user, String pass) throws Exception {
        String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?useSSL=false";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            if (!conn.isClosed()) {
                log.info("Connected to Google Cloud SQL Host " + url);
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

    /**
     * Executes a SQL query and returns the result set.
     *
     * @param conn        The database connection.
     * @param queryString The SQL query to execute.
     * @return The result set of the query.
     * @throws Exception If an error occurs while executing the query.
     */
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

    /**
     * Retrieves a single string value from the result of a SQL query.
     *
     * @param conn        The database connection.
     * @param queryString The SQL query to execute.
     * @return The string value from the result set.
     * @throws Exception If an error occurs while executing the query.
     */
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

    /**
     * Retrieves a list of values from the result of a SQL query.
     *
     * @param conn        The database connection.
     * @param queryString The SQL query to execute.
     * @return A list of values from the result set.
     * @throws Exception If an error occurs while executing the query.
     */
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

    /**
     * Retrieves a status code value from the result of a SQL query.
     *
     * @param conn        The database connection.
     * @param queryString The SQL query to execute.
     * @return The status code value from the result set.
     * @throws Exception If an error occurs while executing the query.
     */
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

    /**
     * Executes a count query and returns the result.
     *
     * @param conn        The database connection.
     * @param queryString The SQL query to execute.
     * @return The count result of the query.
     * @throws Exception If an error occurs while executing the query.
     */
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

    /**
     * Executes a non-query SQL statement (e.g., INSERT, UPDATE, DELETE).
     *
     * @param queryString The SQL statement to execute.
     * @return The number of affected rows.
     * @throws Exception If an error occurs while executing the statement.
     */
    public static int executeNonQuery(String queryString) throws Exception {
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

    /**
     * Converts a result set to a list of maps.
     *
     * @param resultSet The result set to convert.
     * @return A list of maps representing the result set.
     * @throws Exception If an error occurs while processing the result set.
     */
    public static List<Map<String, Object>> resultSetToArrayList(ResultSet resultSet) throws Exception {
        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> list = new ArrayList<>();

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

    /**
     * Closes the database connection.
     *
     * @param conn The database connection to close.
     * @throws Exception If an error occurs while closing the connection.
     */
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
     *
     * @param sourceConn  The source database connection.
     * @param sourceQuery The SQL query for the source table.
     * @param targetConn  The target database connection.
     * @param targetQuery The SQL query for the target table.
     * @return True if the row counts match, false otherwise.
     * @throws Exception If an error occurs while executing the queries.
     */
    public static boolean validateRowCount(Connection sourceConn, String sourceQuery, Connection targetConn, String targetQuery) throws Exception {
        int sourceCount = countQuery(sourceConn, sourceQuery);
        int targetCount = countQuery(targetConn, targetQuery);
        return sourceCount == targetCount;
    }

    /**
     * Validates that data between source and target tables matches.
     *
     * @param sourceConn  The source database connection.
     * @param sourceQuery The SQL query for the source table.
     * @param targetConn  The target database connection.
     * @param targetQuery The SQL query for the target table.
     * @return True if the data matches, false otherwise.
     * @throws Exception If an error occurs while executing the queries.
     */
    public static boolean validateDataIntegrity(Connection sourceConn, String sourceQuery, Connection targetConn, String targetQuery) throws Exception {
        ResultSet sourceResultSet = executeQuery(sourceConn, sourceQuery);
        ResultSet targetResultSet = executeQuery(targetConn, targetQuery);

        List<Map<String, Object>> sourceData = resultSetToArrayList(sourceResultSet);
        List<Map<String, Object>> targetData = resultSetToArrayList(targetResultSet);

        return sourceData.equals(targetData);
    }

    /**
     * Validates that the schema (column names and types) between source and target tables matches.
     *
     * @param sourceConn  The source database connection.
     * @param sourceQuery The SQL query for the source table.
     * @param targetConn  The target database connection.
     * @param targetQuery The SQL query for the target table.
     * @return True if the schemas match, false otherwise.
     * @throws Exception If an error occurs while executing the queries.
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
            if (!sourceMetaData.getColumnName(i).equals(targetMetaData.getColumnName(i)) || sourceMetaData.getColumnType(i) != targetMetaData.getColumnType(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates that there are no unexpected null values in critical columns.
     *
     * @param conn            The database connection.
     * @param query           The SQL query to execute.
     * @param criticalColumns The list of critical columns to check for null values.
     * @return True if there are no unexpected null values, false otherwise.
     * @throws Exception If an error occurs while executing the query.
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
     *
     * @param sourceConn  The source database connection.
     * @param sourceQuery The SQL query for the source table.
     * @param targetConn  The target database connection.
     * @param targetQuery The SQL query for the target table.
     * @return True if the data types match, false otherwise.
     * @throws Exception If an error occurs while executing the queries.
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
     *
     * @param conn       The database connection.
     * @param query      The SQL query to execute.
     * @param keyColumns The list of key columns to check for duplicates.
     * @return True if there are no duplicate rows, false otherwise.
     * @throws Exception If an error occurs while executing the query.
     */
    public static boolean validateNoDuplicates(Connection conn, String query, List<String> keyColumns) throws Exception {
        ResultSet resultSet = executeQuery(conn, query);

        Map<String, Integer> rowMap = new HashMap<>();
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

    /**
     * Inserts data into a table.
     *
     * @param conn      The database connection.
     * @param tableName The name of the table.
     * @param data      A map of column names and values to insert.
     * @return The number of affected rows.
     * @throws Exception If an error occurs while executing the insert.
     */
    public static int insertData(Connection conn, String tableName, Map<String, Object> data) throws Exception {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            columns.append(entry.getKey()).append(",");
            values.append("'").append(entry.getValue()).append("',");
        }

        // Remove trailing commas
        columns.setLength(columns.length() - 1);
        values.setLength(values.length() - 1);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);

        return executeNonQuery(query);
    }

    /**
     * Updates data in a table.
     *
     * @param conn      The database connection.
     * @param tableName The name of the table.
     * @param data      A map of column names and values to update.
     * @param condition The condition for the update.
     * @return The number of affected rows.
     * @throws Exception If an error occurs while executing the update.
     */
    public static int updateData(Connection conn, String tableName, Map<String, Object> data, String condition) throws Exception {
        StringBuilder setClause = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            setClause.append(entry.getKey()).append("='").append(entry.getValue()).append("',");
        }

        // Remove trailing comma
        setClause.setLength(setClause.length() - 1);

        String query = String.format("UPDATE %s SET %s WHERE %s", tableName, setClause, condition);

        return executeNonQuery(query);
    }

    /**
     * Deletes data from a table.
     *
     * @param conn      The database connection.
     * @param tableName The name of the table.
     * @param condition The condition for the delete.
     * @return The number of affected rows.
     * @throws Exception If an error occurs while executing the delete.
     */
    public static int deleteData(Connection conn, String tableName, String condition) throws Exception {
        String query = String.format("DELETE FROM %s WHERE %s", tableName, condition);

        return executeNonQuery(query);
    }

    /**
     * Retrieves data from a table.
     *
     * @param conn      The database connection.
     * @param tableName The name of the table.
     * @param columns   The columns to retrieve.
     * @param condition The condition for the retrieval.
     * @return A list of maps representing the rows retrieved.
     * @throws Exception If an error occurs while executing the retrieval.
     */
    public static List<Map<String, Object>> getData(Connection conn, String tableName, List<String> columns, String condition) throws Exception {
        StringBuilder columnList = new StringBuilder();

        for (String column : columns) {
            columnList.append(column).append(",");
        }

        // Remove trailing comma
        columnList.setLength(columnList.length() - 1);

        String query = String.format("SELECT %s FROM %s WHERE %s", columnList, tableName, condition);

        ResultSet resultSet = executeQuery(conn, query);

        return resultSetToArrayList(resultSet);
    }
}
