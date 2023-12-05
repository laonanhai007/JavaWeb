package com.example.util;

import java.sql.*;

public class JDBCUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "666";
    private static final String URL = "jdbc:mysql://localhost:3306/javaweb?characterEncoding=utf-8";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }

    public static void close(PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }
}
