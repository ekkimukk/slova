package com.ekkimukk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCardServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String visitorName = request.getParameter("visitorName");
        int libraryId = Integer.parseInt(request.getParameter("libraryId"));
        boolean canBorrow = true;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert a new card
            String insertCardSql = "INSERT INTO cards (registration_place_id, can_borrow) VALUES (?, ?)";
            try (PreparedStatement insertCardStmt = connection.prepareStatement(insertCardSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertCardStmt.setInt(1, libraryId);
                insertCardStmt.setBoolean(2, canBorrow);
                insertCardStmt.executeUpdate();

                // Get the generated card ID
                try (ResultSet generatedKeys = insertCardStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int cardId = generatedKeys.getInt(1);

                        // Insert a new visitor linked to the card
                        String insertVisitorSql = "INSERT INTO visitors (name, card_id) VALUES (?, ?)";
                        try (PreparedStatement insertVisitorStmt = connection.prepareStatement(insertVisitorSql)) {
                            insertVisitorStmt.setString(1, visitorName);
                            insertVisitorStmt.setInt(2, cardId);
                            insertVisitorStmt.executeUpdate();
                        }
                    }
                }
            }

            // Redirect with success message
            request.setAttribute("message", "Card successfully created for visitor: " + visitorName);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while creating the card.");
        }

        request.getRequestDispatcher("nedopsa.jsp").forward(request, response);
    }
}

