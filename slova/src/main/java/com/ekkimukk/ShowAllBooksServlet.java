package com.ekkimukk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAllBooksServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        try {
            String url = "jdbc:mariadb://localhost:3306/library_db";
            String user = "library_user";
            String password = "password";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, title, author, number_of_copies, year_of_publication FROM books")) {

                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"), 
                        rs.getString("title"), 
                        rs.getString("author"), 
                        rs.getInt("year_of_publication"),
                        rs.getString("1"), 
                        rs.getInt("number_of_copies")
                    );
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching books.");
        }

        request.setAttribute("books", books);
        request.getRequestDispatcher("manageBooks.jsp").forward(request, response);
    }
}

