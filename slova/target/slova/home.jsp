<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ekkimukk.DatabaseManager" %>
<%@ page import="com.ekkimukk.Book" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.ParseException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Home</title>
    <link href="https://fonts.googleapis.com/css2?family=Funnel+Display:wght@300..800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/home.css">
</head>
<body>
    <%--
    <h2>User: <%=session.getAttribute("username")%></h2>
    --%>
    <header>
        <h1>Slova LIBRARY</h1>
    </header>

        <%-- THREE OPTIONS --%>
        <form action="" method="get">
            <button class="button-56" id="button-green" type="submit" formaction="./inProgress.jsp">Допса</button>
            <br>
            <button class="button-56" id="button-blue" type="submit" formaction="./game/index.html">Entertainment</button>
            <br>
            <button class="button-56" id="button-red" type="submit" formaction="./manageBooks.jsp">Manage books</button>
            <br><br>
        </form>

        <%-- SHOW ALL BOOKS --%>
        <form action="listAllBooks" method="get">
            <button class="button-56" id="button-show" type="submit">Show all books</button>
            <br>
        </form>

        <%-- SEARCH --%>
        <form action="searchBooks" method="get">
            <div class="container">
                <input type="text" name="query" placeholder="Search by title, author, or year">
            </div>
            <br>
            <button class="button-56" id="button-search" type="submit">Search</button>
            <br>
        </form>

    <table>
        <%
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null && !books.isEmpty()) {
        %>
        <thead>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Number Of Copies</th>
                <th>Year Of Publication</th>
            </tr>
        </thead>
        <%
                for (Book book : books) {
        %>
        <tbody>
        <tr>
            <td><%= book.getTitle() %></td>
            <td><%= book.getAuthor() %></td>
            <td><%= book.getNumberOfCopies() %></td>
            <td><%= book.getYearOfPublication() %></td>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
                <p><strong><%= message %></strong></p>
            <%
                }
            %>

            <%
                }
            } else if (books != null) {
            %>
                <tr><td colspan="5">No books found.</td></tr>
            <%
            }
            %>
        </tbody>
    </table>

<footer class="footer">
    <p>Created and posted by ekkimukk.</p>
    <p>Copyright (c) 2024. All Rights Reserved.</p>
</footer>

<%--
 --%>
</body>
</html>
