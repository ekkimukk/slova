<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Library Operations</title>
    <link href="https://fonts.googleapis.com/css2?family=Funnel+Display:wght@300..800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/nedopsa.css">
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
        <button class="button-56" type="submit">Create Card</button>
    </form>




    <form action="requestBook" method="post">
        <h2>Request a Book Transfer</h2>
        <label for="bookId">Select Book:</label>
        <select id="bookId" name="bookId">
            <%-- Dynamically populate books --%>
            <% 
                try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library_db", "library_user", "password");
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT books.id, books.title FROM books")) {
                    while (rs.next()) {
            %>
                        <option value="<%= rs.getInt("id") %>"><%= rs.getString("title") %></option>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <br><br>
        <label for="fromLibraryId">Source Library:</label>
        <select id="fromLibraryId" name="fromLibraryId">
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
        <label for="toLibraryId">Destination Library:</label>
        <select id="toLibraryId" name="toLibraryId">
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
        <button class="button-56" type="submit">Request Transfer</button>
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

    <footer class="footer">
        <p>Created and posted by ekkimukk.</p>
        <p>Copyright (c) 2024. All Rights Reserved.</p>
    </footer>
</body>
</html>

