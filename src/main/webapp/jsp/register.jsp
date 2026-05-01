<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | AutoCare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<main class="auth-card">
    <h1>Create Account</h1>
    <p class="muted">Start booking services for your car or bike.</p>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/register" method="post" class="form" data-validate>
        <label>Name</label>
        <input type="text" name="name" minlength="2" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" minlength="6" required>

        <button type="submit" class="btn primary">Register</button>
    </form>

    <p class="link-line">Already registered? <a href="${pageContext.request.contextPath}/login">Login</a></p>
</main>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
