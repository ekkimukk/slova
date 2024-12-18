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
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form data
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String numberOfCopiesStr = request.getParameter("numberOfCopies");
        String yearOfPublicationStr = request.getParameter("yearOfPublication");

        int numberOfCopies;
        int yearOfPublication;

        try {
            numberOfCopies = Integer.parseInt(numberOfCopiesStr);
            yearOfPublication = Integer.parseInt(yearOfPublicationStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("<p>Invalid input for number of copies or year of publication. Please enter valid integers.</p>");
            return;
        }

        // Database interaction
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            String sql = "INSERT INTO books (title, author, number_of_copies, year_of_publication) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                preparedStatement.setInt(3, numberOfCopies);
                preparedStatement.setInt(4, yearOfPublication);

                int rowsInserted = preparedStatement.executeUpdate();

                try (PrintWriter out = response.getWriter()) {
                    if (rowsInserted > 0) {
                        out.println("<p>Book added successfully!</p>");
                    } else {
                        out.println("<p>Failed to add the book. Please try again.</p>");
                    }
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}

