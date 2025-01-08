package com.ekkimukk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestBookServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String fromLibraryId = request.getParameter("fromLibraryId");
        String toLibraryId = request.getParameter("toLibraryId");

        if (bookId == null || fromLibraryId == null || toLibraryId == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("nedopsa.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO book_transfers (book_id, from_library_id, to_library_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(bookId));
                pstmt.setInt(2, Integer.parseInt(fromLibraryId));
                pstmt.setInt(3, Integer.parseInt(toLibraryId));
                pstmt.executeUpdate();
            }
            request.setAttribute("message", "Book transfer request created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request.");
        }

        request.getRequestDispatcher("nedopsa.jsp").forward(request, response);
    }
}

