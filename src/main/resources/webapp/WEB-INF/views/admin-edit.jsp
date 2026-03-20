<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Feedback | Customer Feedback</title>
</head>
<body>
<h2>Edit Feedback</h2>

<form action="/admin/edit/${feedback.id}" method="post">
    <p>
        <label for="message">Feedback Message</label><br>
        <textarea id="message" name="message" rows="8" cols="60" required>${feedback.message}</textarea>
    </p>
    <button type="submit">Update</button>
</form>

<p><a href="/admin/dashboard">Back to Dashboard</a></p>
</body>
</html>
