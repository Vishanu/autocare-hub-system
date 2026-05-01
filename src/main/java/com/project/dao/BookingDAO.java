package com.project.dao;

import com.project.model.Booking;
import com.project.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public boolean isSlotAvailable(int vehicleId, Date date, Time time) throws SQLException {
        String sql = "SELECT id FROM bookings WHERE vehicle_id = ? AND `date` = ? AND `time` = ? " +
                "AND status <> 'Cancelled'";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleId);
            statement.setDate(2, date);
            statement.setTime(3, time);
            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next();
            }
        }
    }

    public boolean createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, vehicle_id, service_id, `date`, `time`, status) " +
                "VALUES (?, ?, ?, ?, ?, 'Pending')";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getVehicleId());
            statement.setInt(3, booking.getServiceId());
            statement.setDate(4, booking.getDate());
            statement.setTime(5, booking.getTime());
            return statement.executeUpdate() == 1;
        }
    }

    public List<Booking> findByUserId(int userId) throws SQLException {
        String sql = baseSelect() + " WHERE b.user_id = ? ORDER BY b.`date` DESC, b.`time` DESC";
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(mapBooking(resultSet));
                }
            }
        }
        return bookings;
    }

    public List<Booking> findAll() throws SQLException {
        String sql = baseSelect() + " ORDER BY b.`date` DESC, b.`time` DESC";
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                bookings.add(mapBooking(resultSet));
            }
        }
        return bookings;
    }

    public boolean updateStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, bookingId);
            return statement.executeUpdate() == 1;
        }
    }

    private String baseSelect() {
        return "SELECT b.id, b.user_id, b.vehicle_id, b.service_id, b.`date` AS booking_date, b.`time` AS booking_time, b.status, " +
                "u.name AS user_name, v.number AS vehicle_number, v.model AS vehicle_model, " +
                "s.name AS service_name, s.price AS service_price " +
                "FROM bookings b " +
                "JOIN users u ON u.id = b.user_id " +
                "JOIN vehicles v ON v.id = b.vehicle_id " +
                "JOIN services s ON s.id = b.service_id";
    }

    private Booking mapBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setId(resultSet.getInt("id"));
        booking.setUserId(resultSet.getInt("user_id"));
        booking.setVehicleId(resultSet.getInt("vehicle_id"));
        booking.setServiceId(resultSet.getInt("service_id"));
        booking.setDate(resultSet.getDate("booking_date"));
        booking.setTime(resultSet.getTime("booking_time"));
        booking.setStatus(resultSet.getString("status"));
        booking.setUserName(resultSet.getString("user_name"));
        booking.setVehicleNumber(resultSet.getString("vehicle_number"));
        booking.setVehicleModel(resultSet.getString("vehicle_model"));
        booking.setServiceName(resultSet.getString("service_name"));
        booking.setServicePrice(resultSet.getBigDecimal("service_price"));
        return booking;
    }
}
