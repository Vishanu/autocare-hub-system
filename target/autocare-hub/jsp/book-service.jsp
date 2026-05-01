<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Vehicle" %>
<%@ page import="com.project.model.Service" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Service | AutoCare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/dashboard">AutoCare Hub</a>
    <div>
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/vehicles">Vehicles</a>
        <a href="${pageContext.request.contextPath}/history">History</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<main class="container narrow">
    <section class="page-head">
        <div>
            <h1>Book a Service</h1>
            <p class="muted">Choose a vehicle, service, date, and time slot.</p>
        </div>
    </section>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>

    <%
        List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("vehicles");
        List<Service> services = (List<Service>) request.getAttribute("services");
        if (vehicles == null || vehicles.isEmpty()) {
    %>
    <div class="panel">
        <p class="muted">Add a vehicle before booking a service.</p>
        <a class="btn primary" href="${pageContext.request.contextPath}/vehicles">Add Vehicle</a>
    </div>
    <% } else { %>
    <form action="${pageContext.request.contextPath}/book" method="post" class="panel form" data-validate>
        <label>Vehicle</label>
        <select name="vehicleId" required>
            <option value="">Select vehicle</option>
            <% for (Vehicle vehicle : vehicles) { %>
            <option value="<%= vehicle.getId() %>"><%= vehicle.getType() %> - <%= vehicle.getModel() %> (<%= vehicle.getNumber() %>)</option>
            <% } %>
        </select>

        <label>Service</label>
        <select name="serviceId" required>
            <option value="">Select service</option>
            <% for (Service service : services) { %>
            <option value="<%= service.getId() %>" data-price="<%= service.getPrice() %>">
                <%= service.getName() %> - Rs. <%= service.getPrice() %>
            </option>
            <% } %>
        </select>

        <label>Date</label>
        <input type="date" name="date" required data-today>

        <label>Time Slot</label>
        <select name="time" required>
            <option value="">Select time</option>
            <option value="08:00">08:00</option>
            <option value="09:00">09:00</option>
            <option value="10:00">10:00</option>
            <option value="11:00">11:00</option>
            <option value="12:00">12:00</option>
            <option value="14:00">14:00</option>
            <option value="15:00">15:00</option>
            <option value="16:00">16:00</option>
            <option value="17:00">17:00</option>
            <option value="18:00">18:00</option>
        </select>

        <div class="price-box">Estimated Price: <strong id="pricePreview">Rs. 0.00</strong></div>

        <button type="submit" class="btn primary">Confirm Booking</button>
    </form>
    <% } %>
</main>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
