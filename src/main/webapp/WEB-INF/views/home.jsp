<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback | Customer Feedback</title>
</head>
<body>
<h2>Edit / Add feedback: User can choose to see previously submitted feedback or add new if not submitted.</h2>

<h3>Feedback</h3>
<p>Logged in user: ${employee.username}</p>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:if test="${not empty success}">
    <p>${success}</p>
</c:if>

<form action="/feedback/save" method="post">
    <p>Your Feedback:</p>
    <textarea name="message" rows="8" cols="60" placeholder="Write your feedback here..."><c:out value="${feedback.message}"/></textarea>
    <br><br>
    <button type="submit" name="action" value="add">Add Feedback</button>
    <button type="submit" name="action" value="edit">Edit Feedback</button>
</form>

<p><a href="/logout">Log Out</a></p>
</body>
</html>
