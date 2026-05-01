package com.project.service;

import com.project.dao.BookingDAO;
import com.project.dao.ServiceDAO;
import com.project.dao.VehicleDAO;
import com.project.model.Booking;
import com.project.model.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();

    public void book(int userId, int vehicleId, int serviceId, LocalDate date, LocalTime time)
            throws SQLException, IllegalArgumentException {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Please select today or a future date.");
        }
        if (time == null || time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("Please select a time between 08:00 and 18:00.");
        }
        if (!vehicleDAO.belongsToUser(vehicleId, userId)) {
            throw new IllegalArgumentException("Selected vehicle is not valid for this account.");
        }
        Service service = serviceDAO.findById(serviceId);
        if (service == null || !service.isActive()) {
            throw new IllegalArgumentException("Selected service is not available.");
        }

        Date sqlDate = Date.valueOf(date);
        Time sqlTime = Time.valueOf(time);
        if (!bookingDAO.isSlotAvailable(vehicleId, sqlDate, sqlTime)) {
            throw new IllegalArgumentException("This vehicle already has a booking for the selected slot.");
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setVehicleId(vehicleId);
        booking.setServiceId(serviceId);
        booking.setDate(sqlDate);
        booking.setTime(sqlTime);

        try {
            bookingDAO.createBooking(booking);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException("This slot was just booked. Please choose another time.");
        }
    }
}
