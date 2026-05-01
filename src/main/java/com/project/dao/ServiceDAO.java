package com.project.dao;

import com.project.model.Service;
import com.project.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    public boolean addService(Service service) throws SQLException {
        String sql = "INSERT INTO services (name, price, is_active) VALUES (?, ?, true)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getName());
            statement.setBigDecimal(2, service.getPrice());
            return statement.executeUpdate() == 1;
        }
    }

    public boolean updateService(Service service) throws SQLException {
        String sql = "UPDATE services SET name = ?, price = ?, is_active = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getName());
            statement.setBigDecimal(2, service.getPrice());
            statement.setBoolean(3, service.isActive());
            statement.setInt(4, service.getId());
            return statement.executeUpdate() == 1;
        }
    }

    public boolean softDelete(int id) throws SQLException {
        String sql = "UPDATE services SET is_active = false WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    public List<Service> findActive() throws SQLException {
        String sql = "SELECT id, name, price, is_active FROM services WHERE is_active = true ORDER BY name";
        return findBySql(sql);
    }

    public List<Service> findAll() throws SQLException {
        String sql = "SELECT id, name, price, is_active FROM services ORDER BY id DESC";
        return findBySql(sql);
    }

    public Service findById(int id) throws SQLException {
        String sql = "SELECT id, name, price, is_active FROM services WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapService(resultSet);
                }
            }
        }
        return null;
    }

    private List<Service> findBySql(String sql) throws SQLException {
        List<Service> services = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                services.add(mapService(resultSet));
            }
        }
        return services;
    }

    private Service mapService(ResultSet resultSet) throws SQLException {
        return new Service(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getBigDecimal("price"),
                resultSet.getBoolean("is_active")
        );
    }
}
