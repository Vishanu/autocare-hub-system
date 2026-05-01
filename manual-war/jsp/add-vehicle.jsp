<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Vehicle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicles | AutoCare Hub</title>
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
            <h1>Vehicles</h1>
            <p class="muted">Add cars and bikes linked to your account.</p>
        </div>
    </section>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getParameter("success") != null) { %>
    <div class="alert success"><%= request.getParameter("success") %></div>
    <% } %>

    <section class="grid two">
        <form action="${pageContext.request.contextPath}/vehicles" method="post" class="panel form" data-validate>
            <h2>Add Vehicle</h2>
            <label>Vehicle Type</label>
            <select name="type" required>
                <option value="">Select type</option>
                <option value="Car">Car</option>
                <option value="Bike">Bike</option>
            </select>

            <label>Model Name</label>
            <input type="text" name="model" minlength="2" required>

            <label>Vehicle Number</label>
            <input type="text" name="number" minlength="4" placeholder="KA01AB1234" required>

            <button type="submit" class="btn primary">Add Vehicle</button>
        </form>

        <div class="panel">
            <h2>Saved Vehicles</h2>
            <%
                List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("vehicles");
                if (vehicles == null || vehicles.isEmpty()) {
            %>
            <p class="muted">No vehicles added yet.</p>
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
    </section>
</main>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
