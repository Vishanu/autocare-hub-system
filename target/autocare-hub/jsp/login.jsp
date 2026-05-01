<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | AutoCare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<main class="auth-card">
    <h1>AutoCare Hub</h1>
    <p class="muted">Login to book and manage vehicle services.</p>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
    <div class="alert success"><%= request.getAttribute("success") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post" class="form">
        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit" class="btn primary">Login</button>
    </form>

    <p class="link-line">New customer? <a href="${pageContext.request.contextPath}/register">Create account</a></p>
    <p class="hint">Admin demo: admin@autocare.com / admin123</p>
</main>
</body>
</html>
