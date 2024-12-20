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

public class UpdateBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form data
        String bookIdStr = request.getParameter("bookId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String numberOfCopiesStr = request.getParameter("numberOfCopies");
        String yearOfPublicationStr = request.getParameter("yearOfPublication");

        // Validate and parse numeric inputs
        int bookId, numberOfCopies, yearOfPublication;
        try {
            bookId = Integer.parseInt(bookIdStr);
            numberOfCopies = Integer.parseInt(numberOfCopiesStr);
            yearOfPublication = Integer.parseInt(yearOfPublicationStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("<h1>Invalid input for number of copies, year of publication, or book ID. Please enter valid integers.</h1>");
            return;
        }

        // Database interaction
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE books SET title = ?, author = ?, number_of_copies = ?, year_of_publication = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                preparedStatement.setInt(3, numberOfCopies);
                preparedStatement.setInt(4, yearOfPublication);
                preparedStatement.setInt(5, bookId);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Redirect back to the manageBooks page after successful update
                    request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
                } else {
                    response.getWriter().println("<h1>Failed to update the book. Please try again.</h1>");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}
