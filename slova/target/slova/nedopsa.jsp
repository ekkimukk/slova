<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Library Operations</title>
</head>
<body>
    <header>
        <h1>Library Operations</h1>
    </header>

    <form action="createCard" method="post">
        <h2>Create a Library Card</h2>
        <label for="visitorName">Visitor Name:</label>
        <input type="text" id="visitorName" name="visitorName" required>
        <br><br>
        <label for="libraryId">Library for Registration:</label>
        <select id="libraryId" name="libraryId">
            <%-- Dynamically populate libraries --%>
            <% 
                try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library_db", "library_user", "password");
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM libraries")) {
                    while (rs.next()) {
            %>
                        <option value="<%= rs.getInt("id") %>"><%= rs.getString("name") %></option>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <br><br>
        <button type="submit">Create Card</button>
    </form>




    <form action="requestBook" method="post">
        <h2>Request a Book Transfer</h2>
        <label for="bookId">Select Book:</label>
        <select id="bookId" name="bookId">
            <%-- Dynamically populate books --%>
            <% 
                try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library_db", "library_user", "password");
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT books.id AS book_id, books.title AS book_title FROM books")) {
                    while (rs.next()) {
            %>
                        <option value="<%= rs.getInt("book_id") %>"><%= rs.getString("book_title") %></option>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <br><br>
        <label for="sourceLibrary">Source Library:</label>
        <select id="sourceLibrary" name="sourceLibrary">
            <%-- Dynamically populate libraries --%>
            <% 
                try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library_db", "library_user", "password");
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM libraries")) {
                    while (rs.next()) {
            %>
                        <option value="<%= rs.getInt("id") %>"><%= rs.getString("name") %></option>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <br><br>
        <label for="destinationLibrary">Destination Library:</label>
        <select id="destinationLibrary" name="destinationLibrary">
            <%-- Dynamically populate libraries --%>
            <% 
                try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library_db", "library_user", "password");
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM libraries")) {
                    while (rs.next()) {
            %>
                        <option value="<%= rs.getInt("id") %>"><%= rs.getString("name") %></option>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <br><br>
        <button type="submit">Request Transfer</button>
    </form>

    <!-- Display messages -->
    <%
        String message = (String) request.getAttribute("message");
        String error = (String) request.getAttribute("error");
        if (message != null) {
    %>
        <div class="message success">
            <p><%= message %></p>
        </div>
    <%
        }
        if (error != null) {
    %>
        <div class="message error">
            <p><%= error %></p>
        </div>
    <%
        }
    %>

    <footer>
        <p>Created and posted by ekkimukk.</p>
        <p>Copyright (c) 2024. All Rights Reserved.</p>
    </footer>
</body>
</html>

