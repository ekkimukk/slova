package com.ekkimukk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LendBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int bookId = Integer.parseInt(request.getParameter("bookId"));
    int cardId = Integer.parseInt(request.getParameter("cardId"));
    String returnDateStr = request.getParameter("returnDate");

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        connection.setAutoCommit(false);

        // Check if cardId exists
        String checkCardSql = "SELECT COUNT(*) FROM cards WHERE card_id = ?";
        PreparedStatement checkCardStmt = connection.prepareStatement(checkCardSql);
        checkCardStmt.setInt(1, cardId);
        ResultSet rs = checkCardStmt.executeQuery();
        if (!rs.next() || rs.getInt(1) == 0) {
            connection.rollback();
            request.setAttribute("message", "Invalid card ID.");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        // Check if book is available
        String checkBookSql = "SELECT number_of_copies FROM books WHERE book_id = ?";
        PreparedStatement checkBookStmt = connection.prepareStatement(checkBookSql);
        checkBookStmt.setInt(1, bookId);
        rs = checkBookStmt.executeQuery();
        if (!rs.next() || rs.getInt("number_of_copies") <= 0) {
            connection.rollback();
            request.setAttribute("message", "This book is not available.");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        // Parse returnDateStr to LocalDate
        LocalDate localDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            localDate = LocalDate.parse(returnDateStr, formatter);
        } catch (DateTimeParseException e) {
            connection.rollback();
            request.setAttribute("message", "Invalid return date format.");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        // Convert LocalDate to java.sql.Date
        java.sql.Date sqlReturnDate = java.sql.Date.valueOf(localDate);

        // Decrease available copies
        String updateBookSql = "UPDATE books SET number_of_copies = number_of_copies - 1 WHERE book_id = ?";
        PreparedStatement updateBookStmt = connection.prepareStatement(updateBookSql);
        updateBookStmt.setInt(1, bookId);
        updateBookStmt.executeUpdate();

        // Insert into card_book
        String insertCardBookSql = "INSERT INTO card_book (card_id, book_id, return_date) VALUES (?, ?, ?)";
        PreparedStatement insertCardBookStmt = connection.prepareStatement(insertCardBookSql);
        insertCardBookStmt.setInt(1, cardId);
        insertCardBookStmt.setInt(2, bookId);
        insertCardBookStmt.setDate(3, sqlReturnDate);
        insertCardBookStmt.executeUpdate();

        connection.commit();

        request.setAttribute("message", "Book lent successfully.");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to lend book.");
    }
}}
