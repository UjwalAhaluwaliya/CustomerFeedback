<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Feedback | Customer Feedback</title>
</head>
<body>
<h2>View Feedback</h2>

<p><strong>ID:</strong> ${feedback.id}</p>
<p><strong>User:</strong> ${feedback.employee.username}</p>
<p><strong>Date:</strong> ${feedback.submittedDate}</p>
<p><strong>Message:</strong></p>
<p>${feedback.message}</p>

<p><a href="/admin/dashboard">Back to Dashboard</a></p>
</body>
</html>
