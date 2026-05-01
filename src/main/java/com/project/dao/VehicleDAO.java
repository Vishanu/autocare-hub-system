package com.project.dao;

import com.project.model.Vehicle;
import com.project.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    public boolean addVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicles (user_id, type, model, number) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicle.getUserId());
            statement.setString(2, vehicle.getType());
            statement.setString(3, vehicle.getModel());
            statement.setString(4, vehicle.getNumber().toUpperCase());
            return statement.executeUpdate() == 1;
        }
    }

    public List<Vehicle> findByUserId(int userId) throws SQLException {
        String sql = "SELECT id, user_id, type, model, number FROM vehicles WHERE user_id = ? ORDER BY id DESC";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(mapVehicle(resultSet));
                }
            }
        }
        return vehicles;
    }

    public List<Vehicle> findAll() throws SQLException {
        String sql = "SELECT v.id, v.user_id, v.type, v.model, v.number, u.name AS owner_name " +
                "FROM vehicles v JOIN users u ON u.id = v.user_id ORDER BY v.id DESC";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Vehicle vehicle = mapVehicle(resultSet);
                vehicle.setOwnerName(resultSet.getString("owner_name"));
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    public boolean belongsToUser(int vehicleId, int userId) throws SQLException {
        String sql = "SELECT id FROM vehicles WHERE id = ? AND user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleId);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private Vehicle mapVehicle(ResultSet resultSet) throws SQLException {
        return new Vehicle(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("type"),
                resultSet.getString("model"),
                resultSet.getString("number")
        );
    }
}
