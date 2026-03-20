<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Customer Feedback</title>
</head>
<body>
<h2>Admin Dashboard: Admin can perform CRUD operations on the feedback given by users.</h2>
<h3>Admin Feedback Dashboard</h3>

<table border="1" cellpadding="6" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>User</th>
        <th>Feedback</th>
        <th>Date</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${feedbackList}" var="item">
        <tr>
            <td>${item.id}</td>
            <td>${item.employee.username}</td>
            <td>${item.message}</td>
            <td>${item.submittedDate}</td>
            <td>
                <a href="/admin/view/${item.id}">View</a>
                <a href="/admin/edit/${item.id}">Edit</a>
                <form action="/admin/delete/${item.id}" method="post">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="/logout">Log Out</a></p>
</body>
</html>
