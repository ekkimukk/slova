package com.ekkimukk;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static Connection connection;

    static {
        try {
            Class.forName("com.mariadb.jdbc.Driver");
            connection = DatabaseConnector.connect();
            System.out.print("Try\n");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // // Поиск записи в таблице БД "Users"
    // public static Integer findUser(String username, String password) {
    //
    //     // Шаблон поиска записи в таблице БД "Users"
    //     System.out.print("hey from finder\n");
    //     String query = "SELECT user_id FROM Users WHERE username = ? AND password = MD5(?)";
    //
    //     // Подготовка запроса
    //     try (PreparedStatement statement = connection.prepareStatement(query)) {
    //
    //         statement.setString(1, username);
    //         statement.setString(2, password);
    //         ResultSet result = statement.executeQuery();
    //
    //         // Возвращает user_id, если пользователь найден
    //         if (result.next()) {
    //             return result.getInt("user_id");
    //         } else {
    //             return null;
    //         }
    //     }
    //     // Вывод ошибки
    //     catch (SQLException e) {
    //         System.out.println("Error: " + e.getMessage());
    //         return null;
    //     }
    // }

    public static List<Book> getBooks() throws SQLException, ParseException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            int numberOfCopies = resultSet.getInt("number_of_copies");
            int yearOfPublication = resultSet.getInt("year_of_publication");
            books.add(new Book(id, title, author, numberOfCopies, yearOfPublication));
        }
        System.out.printf(books.toString());
        return books;
    }
    //
    // // Обновление записей в таблице БД "Dreams"
    // public static void updateDreams(int user_id, List<Dream> data) throws SQLException {
    //     Statement statement = connection.createStatement();
    //     String query = "DELETE FROM dreams WHERE user = " + user_id;
    //     statement.executeUpdate(query);
    //     for (Dream dream : data) {
    //         new DreamsUpdater(user_id, dream).start();
    //     }
    // }
}

