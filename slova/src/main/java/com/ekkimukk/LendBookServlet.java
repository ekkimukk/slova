package com.ekkimukk;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.SQLException;


public class LendBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры из запроса
        String bookIdStr = request.getParameter("bookId");
        String cardIdStr = request.getParameter("cardID");

        int bookId;
        int cardId;

        // Проверка корректности входных данных
        try {
            bookId = Integer.parseInt(bookIdStr);
            cardId = Integer.parseInt(cardIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Book ID or Card ID. Please provide valid integers.");
            request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
            return;
        }

        // Вызов процедуры lend_book
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String callSql = "{CALL lend_book(?, ?, CURDATE())}";
            try (CallableStatement callableStmt = connection.prepareCall(callSql)) {
                callableStmt.setInt(1, cardId);
                callableStmt.setInt(2, bookId);

                // Выполняем процедуру
                callableStmt.execute();

                // Если всё успешно, перенаправляем на страницу manageBooks.jsp
                request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Устанавливаем сообщение об ошибке
            if ("45000".equals(e.getSQLState())) {
                // Пользовательская ошибка
                request.setAttribute("errorMessage", e.getMessage());
            } else {
                // Другие ошибки базы данных
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            }
            request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
        }
    }
}

