<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Booking" %>
<%@ page import="com.project.model.Service" %>
<%@ page import="com.project.model.User" %>
<%@ page import="com.project.model.Vehicle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel | AutoCare Hub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/admin">AutoCare Hub Admin</a>
    <div>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<main class="container">
    <section class="page-head">
        <div>
            <h1>Admin Panel</h1>
            <p class="muted">Manage services, bookings, users, and vehicles.</p>
        </div>
    </section>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getParameter("success") != null) { %>
    <div class="alert success"><%= request.getParameter("success") %></div>
    <% } %>

    <section class="grid two">
        <form action="${pageContext.request.contextPath}/admin" method="post" class="panel form">
            <h2>Add Service</h2>
            <input type="hidden" name="action" value="addService">
            <label>Service Name</label>
            <input type="text" name="name" required>
            <label>Price</label>
            <input type="number" step="0.01" min="1" name="price" required>
            <button type="submit" class="btn primary">Add Service</button>
        </form>

        <div class="panel">
            <h2>Services</h2>
            <%
                List<Service> services = (List<Service>) request.getAttribute("services");
                if (services == null || services.isEmpty()) {
            %>
            <p class="muted">No services found.</p>
            <% } else { %>
            <table>
                <thead><tr><th>Name</th><th>Price</th><th>Active</th><th>Action</th></tr></thead>
                <tbody>
                <% for (Service service : services) { %>
                <tr>
                    <form action="${pageContext.request.contextPath}/admin" method="post">
                        <td>
                            <input type="hidden" name="action" value="updateService">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>">
                            <input type="text" name="name" value="<%= service.getName() %>" required>
                        </td>
                        <td><input type="number" step="0.01" min="1" name="price" value="<%= service.getPrice() %>" required></td>
                        <td><input type="checkbox" name="active" <%= service.isActive() ? "checked" : "" %>></td>
                        <td class="actions"><button class="btn small" type="submit">Save</button></td>
                    </form>
                </tr>
                <tr>
                    <td colspan="4">
                        <form action="${pageContext.request.contextPath}/admin" method="post" class="inline-form">
                            <input type="hidden" name="action" value="deleteService">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>">
                            <button class="btn danger small" type="submit">Soft Delete</button>
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>
    </section>

    <section class="panel">
        <h2>All Bookings</h2>
        <%
            List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
            if (bookings == null || bookings.isEmpty()) {
        %>
        <p class="muted">No bookings available.</p>
        <% } else { %>
        <table>
            <thead><tr><th>ID</th><th>User</th><th>Vehicle</th><th>Service</th><th>Date</th><th>Time</th><th>Status</th><th>Update</th></tr></thead>
            <tbody>
            <% for (Booking booking : bookings) { %>
            <tr>
                <td>#<%= booking.getId() %></td>
                <td><%= booking.getUserName() %></td>
                <td><%= booking.getVehicleModel() %> (<%= booking.getVehicleNumber() %>)</td>
                <td><%= booking.getServiceName() %> - Rs. <%= booking.getServicePrice() %></td>
                <td><%= booking.getDate() %></td>
                <td><%= booking.getTime() %></td>
                <td><span class="status <%= booking.getStatus().toLowerCase() %>"><%= booking.getStatus() %></span></td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post" class="inline-form">
                        <input type="hidden" name="action" value="updateStatus">
                        <input type="hidden" name="bookingId" value="<%= booking.getId() %>">
                        <select name="status">
                            <option value="Pending" <%= "Pending".equals(booking.getStatus()) ? "selected" : "" %>>Pending</option>
                            <option value="Completed" <%= "Completed".equals(booking.getStatus()) ? "selected" : "" %>>Completed</option>
                            <option value="Cancelled" <%= "Cancelled".equals(booking.getStatus()) ? "selected" : "" %>>Cancelled</option>
                        </select>
                        <button class="btn small" type="submit">Update</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </section>

    <section class="grid two">
        <div class="panel">
            <h2>Users</h2>
            <table>
                <thead><tr><th>Name</th><th>Email</th><th>Role</th></tr></thead>
                <tbody>
                <% for (User user : (List<User>) request.getAttribute("users")) { %>
                <tr><td><%= user.getName() %></td><td><%= user.getEmail() %></td><td><%= user.getRole() %></td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <div class="panel">
            <h2>Vehicles</h2>
            <table>
                <thead><tr><th>Owner</th><th>Type</th><th>Model</th><th>Number</th></tr></thead>
                <tbody>
                <% for (Vehicle vehicle : (List<Vehicle>) request.getAttribute("vehicles")) { %>
                <tr>
                    <td><%= vehicle.getOwnerName() %></td>
                    <td><%= vehicle.getType() %></td>
                    <td><%= vehicle.getModel() %></td>
                    <td><%= vehicle.getNumber() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</main>
</body>
</html>
