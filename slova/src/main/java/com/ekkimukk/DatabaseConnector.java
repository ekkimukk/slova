package com.ekkimukk;

import java.sql.*;

public class DatabaseConnector {

    // Параметры подключения к базе данных
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String ACCESS_USER = "library_user";
    private static final String ACCESS_PASSWORD = "password";

    // Подключение к БД
    public static Connection connection;

    // Получение URL БД
    public static String getDbUrl() {
        return DB_URL;
    }

    // Получение логина root-пользоателя БД
    public static String getUser() {
        return ACCESS_USER;
    }

    // Получение пароля root-пользоателя БД
    public static String getPassword() {
        return ACCESS_PASSWORD;
    }

    // Создание подключения к БД
    public static Connection connect() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("com.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, ACCESS_USER, ACCESS_PASSWORD);
        }
        return connection;
    }

    // Удаления подключения к БД
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            connection = null;
        }
    }

}
