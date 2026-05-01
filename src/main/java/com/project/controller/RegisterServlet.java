package com.project.controller;

import com.project.dao.UserDAO;
import com.project.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = trim(request.getParameter("name"));
        String email = trim(request.getParameter("email"));
        String password = trim(request.getParameter("password"));

        if (name.length() < 2 || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$") || password.length() < 6) {
            request.setAttribute("error", "Enter a valid name, email, and a password of at least 6 characters.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        try {
            if (userDAO.emailExists(email)) {
                request.setAttribute("error", "Email is already registered.");
                request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
                return;
            }
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            userDAO.register(user);
            request.setAttribute("success", "Registration successful. Please login.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database connection failed. Please check MySQL credentials and import schema.sql.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
