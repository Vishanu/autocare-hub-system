<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Booking" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking History | AutoCare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/dashboard">AutoCare Hub</a>
    <div>
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/book">Book Service</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<main class="container">
    <section class="page-head">
        <div>
            <h1>Booking History</h1>
            <p class="muted">Every confirmed, completed, and cancelled service booking.</p>
        </div>
    </section>

    <% if (request.getParameter("success") != null) { %>
    <div class="alert success"><%= request.getParameter("success") %></div>
    <% } %>

    <div class="panel">
        <%
            List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
            if (bookings == null || bookings.isEmpty()) {
        %>
        <p class="muted">No bookings found.</p>
        <% } else { %>
        <table>
            <thead>
            <tr>
                <th>ID</th><th>Vehicle</th><th>Service</th><th>Price</th><th>Date</th><th>Time</th><th>Status</th>
            </tr>
            </thead>
            <tbody>
            <% for (Booking booking : bookings) { %>
            <tr>
                <td>#<%= booking.getId() %></td>
                <td><%= booking.getVehicleModel() %> (<%= booking.getVehicleNumber() %>)</td>
                <td><%= booking.getServiceName() %></td>
                <td>Rs. <%= booking.getServicePrice() %></td>
                <td><%= booking.getDate() %></td>
                <td><%= booking.getTime() %></td>
                <td><span class="status <%= booking.getStatus().toLowerCase() %>"><%= booking.getStatus() %></span></td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</main>
</body>
</html>
