package com.project.controller;

import com.project.dao.BookingDAO;
import com.project.dao.ServiceDAO;
import com.project.dao.VehicleDAO;
import com.project.model.User;
import com.project.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookServiceServlet extends HttpServlet {
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireUser(request, response);
        if (user == null) {
            return;
        }
        String path = request.getServletPath();
        try {
            if ("/history".equals(path)) {
                request.setAttribute("bookings", bookingDAO.findByUserId(user.getId()));
                request.getRequestDispatcher("/jsp/booking-history.jsp").forward(request, response);
                return;
            }
            request.setAttribute("vehicles", vehicleDAO.findByUserId(user.getId()));
            request.setAttribute("services", serviceDAO.findActive());
            request.getRequestDispatcher("/jsp/book-service.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to load booking page", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireUser(request, response);
        if (user == null) {
            return;
        }
        try {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime time = LocalTime.parse(request.getParameter("time"));
            bookingService.book(user.getId(), vehicleId, serviceId, date, time);
            response.sendRedirect(request.getContextPath() + "/history?success=Booking confirmed");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to book service", e);
        }
    }

    private User requireUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return user;
    }
}
