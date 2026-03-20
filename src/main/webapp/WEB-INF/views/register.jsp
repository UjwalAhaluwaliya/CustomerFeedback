<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | Customer Feedback</title>
</head>
<body>
<h2>Registration page</h2>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form action="/register" method="post">
    <h2>Register</h2>
    <p>
        <label for="username">Username</label><br>
        <input id="username" name="username" type="text" required>
    </p>

    <p>
        <label for="email">Email</label><br>
        <input id="email" name="email" type="email" required>
    </p>

    <p>
        <label for="password">Password</label><br>
        <input id="password" name="password" type="password" required>
    </p>

    <p>
        <label for="confirmPassword">Confirm Password</label><br>
        <input id="confirmPassword" name="confirmPassword" type="password" required>
    </p>

    <button type="submit">Sign Up</button>
</form>

<p>Already have an account? <a href="/login">Log In</a></p>
</body>
</html>
