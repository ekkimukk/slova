<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Logout</title>
</head>
<body>

    <%
        // Очищаем атрибуты сессии
        session.invalidate();
    %>

    <p>You have been logged out successfully.</p>
    <p><a href="login.jsp">Click here to login again</a></p>

</body>
</html>

