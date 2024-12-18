<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ekkimukk.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Books</title>
</head>
<body>
    <h1>Edit Books</h1>
    <a href="home.jsp">Back to Home</a>
    <form action="listAllBooks" method="get">
        <button type="submit">Show all books</button>
    </form>

    <table>
        <thead>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Number of Copies</th>
            <th>Year of Publication</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%-- Dynamically populate rows based on book list --%>
        <%
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null && !books.isEmpty()) {
                for (Book book : books) {
        %>
        <tr>
            <form action="updateBook" method="post">
                <input type="hidden" name="bookId" value="<%= book.getId() %>">
                <td><input type="text" name="title" value="<%= book.getTitle() %>" required></td>
                <td><input type="text" name="author" value="<%= book.getAuthor() %>" required></td>
                <td><input type="text" name="numberOfCopies" value="<%= book.getNumberOfCopies() %>" required></td>
                <td><input type="text" name="yearOfPublication" value="<%= book.getYearOfPublication() %>" required></td>
                <td>
                    <button type="submit">Update</button>
                </td>
            </form>
            <td>
                <form action="deleteBook" method="post" style="display:inline;">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button type="submit" style="background-color: #dc3545;">Delete</button>
                </form>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No books found in the database.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <h2>Add New Book</h2>
    <form action="addBook" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" required><br>

        <label for="numberOfCopies">Number of Copies:</label>
        <input type="text" id="numberOfCopies" name="numberOfCopies" required><br>

        <label for="yearOfPublication">Year of Publication:</label>
        <input type="text" id="yearOfPublication" name="yearOfPublication" required><br>

        <button type="submit">Add Book</button>
    </form>
</body>
</html>

