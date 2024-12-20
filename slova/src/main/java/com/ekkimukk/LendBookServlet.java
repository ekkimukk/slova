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
import java.sql.ResultSet;
import java.sql.SQLException;

public class LendBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve parameters from the request
        String bookIdStr = request.getParameter("bookId");
        String cardIdStr = request.getParameter("cardID");

        int bookId;
        int cardId;
        try {
            bookId = Integer.parseInt(bookIdStr);
            cardId = Integer.parseInt(cardIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("<h1>Invalid Book ID. Please provide a valid integer.</h1>");
            return;
        }

        // Database interaction
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check the current number of copies for the book
            String checkCopiesSql = "SELECT number_of_copies FROM books WHERE id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkCopiesSql)) {
                checkStmt.setInt(1, bookId);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int numberOfCopies = rs.getInt("number_of_copies");

                        if (numberOfCopies > 0) {
                            // Reduce the number of copies by 1
                            String updateSql = "UPDATE books SET number_of_copies = number_of_copies - 1 WHERE id = ?";
                            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                                updateStmt.setInt(1, bookId);
                                updateStmt.executeUpdate();
                            }

                            // Record the lending transaction (optional, for audit/logging purposes)
                            String lendSql = "INSERT INTO card_book (book_id, card_id, lend_date) VALUES (?, ?, NOW())";
                            try (PreparedStatement lendStmt = connection.prepareStatement(lendSql)) {
                                lendStmt.setInt(1, bookId);
                                lendStmt.setInt(2, cardId);
                                lendStmt.executeUpdate();
                            }

                            // Redirect back to the book list
                            request.getRequestDispatcher("manageBooks.jsp").forward(request, response);

                        } else {
                            // No copies available
                            try (PrintWriter out = response.getWriter()) {
                                out.println("<h1>Sorry, no copies of this book are available for lending.</h1>");
                            }
                        }
                    } else {
                        // Book not found
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<h1>Book not found. Please check the Book ID.</h1>");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }
}

