<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.ekkimukk.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Books</title>
    <link href="https://fonts.googleapis.com/css2?family=Funnel+Display:wght@300..800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/manageBooks.css">
</head>

<body>

     <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <div style="color: #cc241d; font-weight: bold; margin-bottom: 20px;">
            <%= errorMessage %>
        </div>
    <%
        }
    %>

    <h2>Add New Book</h2>
    <form action="addBook" method="post">
        <label for="title"></label>
        <input type="text" name="title" placeholder="Title" required><br>
        <br>
        <label for="author"></label>
        <input type="text" name="author" placeholder="Author" required><br>
        <br>
        <label for="numberOfCopies"></label>
        <input type="text" name="numberOfCopies" placeholder="Number of Copies" required><br>
        <br>
        <label for="yearOfPublication"></label>
        <input type="text" name="yearOfPublication" placeholder="Year of Publication" required><br>
        <br>
        <button class="button-56" id="button-add" type="submit">Add Book</button>
        <br><br>
    </form>


    <form action="showAllBooks" method="get"> 
        <button type="submit" id="button-show" class="button-56">Show all books</button>
        <br><br>
    </form>

    <table>
        <thead>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Year of Publication</th>
                <th>Number of Copies</th>
            </tr>
        </thead>
        <tbody>
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
                <td><input id="yearOfPublication" type="text" name="yearOfPublication" value="<%= book.getYearOfPublication() %>" required></td>
                <td><input id="numberOfCopies" type="text" name="numberOfCopies" value="<%= book.getNumberOfCopies() %>" required></td>
                <td><button type="submit" class="button-89" role="button">Update</button></td>
            </form>
            <td>
                <form action="deleteBook" method="post">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button type="submit" class="button-89" role="button">Delete</button>
                </form>
            </td>
            <td>
                <form action="lendBook" method="post">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <input id="cardID" type="text" name="cardID" placeholder="Card ID" required>
                    <button type="submit" class="button-89" role="button">Lend</button>
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

</body>
</html>

