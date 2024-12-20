package com.ekkimukk;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the book ID from the form
        String bookIdStr = request.getParameter("bookId");

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("<h1>Invalid book ID. Please provide a valid integer value.</h1>");
            return;
        }

        // Database interaction
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, bookId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    // Redirect to the showAllBooks servlet after successful deletion
                    request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
                } else {
                    response.getWriter().println("<h1>Book not found or could not be deleted.</h1>");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}

