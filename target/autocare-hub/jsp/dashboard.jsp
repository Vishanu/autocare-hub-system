<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Vehicle" %>
<%@ page import="com.project.model.Booking" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | AutoCare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/dashboard">AutoCare Hub</a>
    <div>
        <a href="${pageContext.request.contextPath}/vehicles">Vehicles</a>
        <a href="${pageContext.request.contextPath}/book">Book Service</a>
        <a href="${pageContext.request.contextPath}/history">History</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<main class="container">
    <section class="page-head">
        <div>
            <h1>Welcome, ${sessionScope.user.name}</h1>
            <p class="muted">Manage vehicles, book services, and track every visit.</p>
        </div>
        <a class="btn primary" href="${pageContext.request.contextPath}/book">Book Service</a>
    </section>

    <section class="grid two">
        <div class="panel">
            <h2>Your Vehicles</h2>
            <%
                List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("vehicles");
                if (vehicles == null || vehicles.isEmpty()) {
            %>
            <p class="muted">No vehicles yet.</p>
            <a class="btn" href="${pageContext.request.contextPath}/vehicles">Add Vehicle</a>
            <% } else { %>
            <table>
                <thead><tr><th>Type</th><th>Model</th><th>Number</th></tr></thead>
                <tbody>
                <% for (Vehicle vehicle : vehicles) { %>
                <tr>
                    <td><%= vehicle.getType() %></td>
                    <td><%= vehicle.getModel() %></td>
                    <td><%= vehicle.getNumber() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>

        <div class="panel">
            <h2>Recent Bookings</h2>
            <%
                List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
                if (bookings == null || bookings.isEmpty()) {
            %>
            <p class="muted">No bookings yet.</p>
            <% } else { %>
            <table>
                <thead><tr><th>Service</th><th>Date</th><th>Status</th></tr></thead>
                <tbody>
                <% int count = 0; for (Booking booking : bookings) { if (count++ == 5) break; %>
                <tr>
                    <td><%= booking.getServiceName() %></td>
                    <td><%= booking.getDate() %> <%= booking.getTime() %></td>
                    <td><span class="status <%= booking.getStatus().toLowerCase() %>"><%= booking.getStatus() %></span></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>
    </section>
</main>
</body>
</html>
