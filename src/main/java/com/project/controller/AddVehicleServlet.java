package com.project.controller;

import com.project.dao.VehicleDAO;
import com.project.model.User;
import com.project.model.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddVehicleServlet extends HttpServlet {
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireUser(request, response);
        if (user == null) {
            return;
        }
        try {
            request.setAttribute("vehicles", vehicleDAO.findByUserId(user.getId()));
            request.getRequestDispatcher("/jsp/add-vehicle.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to load vehicles", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = requireUser(request, response);
        if (user == null) {
            return;
        }

        String type = trim(request.getParameter("type"));
        String model = trim(request.getParameter("model"));
        String number = trim(request.getParameter("number"));

        if (!("Car".equals(type) || "Bike".equals(type)) || model.length() < 2 || number.length() < 4) {
            request.setAttribute("error", "Please enter valid vehicle details.");
            doGet(request, response);
            return;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setUserId(user.getId());
        vehicle.setType(type);
        vehicle.setModel(model);
        vehicle.setNumber(number);

        try {
            vehicleDAO.addVehicle(vehicle);
            response.sendRedirect(request.getContextPath() + "/vehicles?success=Vehicle added");
        } catch (SQLException e) {
            throw new ServletException("Unable to add vehicle", e);
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

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
