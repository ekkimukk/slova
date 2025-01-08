package com.ekkimukk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListAllBooksServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String DB_USER = "library_user";
    private static final String DB_PASSWORD = "password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        String sortBy = request.getParameter("sortBy");

        if (sortBy == null || !(sortBy.equals("title") || sortBy.equals("author") || sortBy.equals("year_of_publication"))) {
            sortBy = "title";
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT b.id AS book_id, b.title, b.author, b.year_of_publication, " +
                         "l.name AS library_name, lb.number_of_copies " +
                         "FROM books b " +
                         "JOIN library_book lb ON b.id = lb.book_id " +
                         "JOIN libraries l ON lb.library_id = l.id " +
                         "ORDER BY " + sortBy;

            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("year_of_publication"),
                    rs.getString("library_name"),
                    rs.getInt("number_of_copies")
                );
                books.add(book);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while fetching books.");
            e.printStackTrace();
        }

        request.setAttribute("books", books);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}

