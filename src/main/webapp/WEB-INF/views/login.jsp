<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Customer Feedback</title>
</head>
<body>
<h2>Summary: Users can provide feedback, edit previously submitted feedback.</h2>
<h3>Login for user</h3>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:if test="${not empty success}">
    <p>${success}</p>
</c:if>

<form action="/login" method="post">
    <h2>Login</h2>
    <p>
        <label for="username">Username</label><br>
        <input id="username" name="username" type="text" required>
    </p>

    <p>
        <label for="password">Password</label><br>
        <input id="password" name="password" type="password" required>
    </p>

    <button type="submit">Log In</button>
</form>

<p>Don't have an account? <a href="/register">Sign Up</a></p>
<p>Admin login: username = admin, password = admin123</p>
</body>
</html>
