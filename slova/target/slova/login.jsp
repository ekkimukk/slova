<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Funnel+Display:wght@300..800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
<%--
 --%>
    <title>Login</title>
</head>

<body>
    <div class="container">
        <h1>Login</h1>
        <br>
        <div id="loginForm">
            <form action="<%= request.getContextPath() %>/login" method="post">
                <label for="username"></label>
                <input type="text" id="username" name="username" placeholder="Username" required><br>

                <label for="password"></label>
                <input type="password" id="password" name="password" placeholder="Password" required><br>

                <button class="button-56" type="submit">Login</button>
                <br>
            </form>
            <%
                String error = request.getParameter("error");
                if ("invalid".equals(error)) {
            %>
                <p>Invalid username or password.</p>
            <% } %>
            <p class="href" onclick="location.href='forgotPassword.jsp'">Forgot the password?</p>
        </div>
    </div>
</body>
</html>

