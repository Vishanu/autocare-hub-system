package com.project.controller;

import com.project.dao.BookingDAO;
import com.project.dao.ServiceDAO;
import com.project.dao.UserDAO;
import com.project.dao.VehicleDAO;
import com.project.model.Service;
import com.project.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AdminServlet extends HttpServlet {
    private static final Set<String> STATUSES = new HashSet<>(Arrays.asList("Pending", "Completed", "Cancelled"));

    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final UserDAO userDAO = new UserDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = requireAdmin(request, response);
        if (admin == null) {
            return;
        }
        loadAdminData(request);
        request.getRequestDispatcher("/jsp/admin-panel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = requireAdmin(request, response);
        if (admin == null) {
            return;
        }
        String action = trim(request.getParameter("action"));
        try {
            if ("addService".equals(action)) {
                addService(request);
                response.sendRedirect(request.getContextPath() + "/admin?success=Service added");
            } else if ("updateService".equals(action)) {
                updateService(request);
                response.sendRedirect(request.getContextPath() + "/admin?success=Service updated");
            } else if ("deleteService".equals(action)) {
                int id = Integer.parseInt(request.getParameter("serviceId"));
                serviceDAO.softDelete(id);
                response.sendRedirect(request.getContextPath() + "/admin?success=Service removed");
            } else if ("updateStatus".equals(action)) {
                int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                String status = trim(request.getParameter("status"));
                if (!STATUSES.contains(status)) {
                    throw new IllegalArgumentException("Invalid booking status.");
                }
                bookingDAO.updateStatus(bookingId, status);
                response.sendRedirect(request.getContextPath() + "/admin?success=Booking status updated");
            } else {
                throw new IllegalArgumentException("Unknown admin action.");
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to process admin action", e);
        }
    }

    private void addService(HttpServletRequest request) throws SQLException {
        Service service = buildService(request);
        serviceDAO.addService(service);
    }

    private void updateService(HttpServletRequest request) throws SQLException {
        Service service = buildService(request);
        service.setId(Integer.parseInt(request.getParameter("serviceId")));
        service.setActive("on".equals(request.getParameter("active")));
        serviceDAO.updateService(service);
    }

    private Service buildService(HttpServletRequest request) {
        String name = trim(request.getParameter("name"));
        BigDecimal price = new BigDecimal(trim(request.getParameter("price")));
        if (name.length() < 2 || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Enter a valid service name and positive price.");
        }
        Service service = new Service();
        service.setName(name);
        service.setPrice(price);
        service.setActive(true);
        return service;
    }

    private void loadAdminData(HttpServletRequest request) throws ServletException {
        try {
            request.setAttribute("services", serviceDAO.findAll());
            request.setAttribute("bookings", bookingDAO.findAll());
            request.setAttribute("users", userDAO.findAllUsers());
            request.setAttribute("vehicles", vehicleDAO.findAll());
        } catch (SQLException e) {
            throw new ServletException("Unable to load admin data", e);
        }
    }

    private User requireAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        if (!user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        return user;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
