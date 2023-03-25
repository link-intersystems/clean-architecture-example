package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class H2ReturnCarRepositoryTest {

    private H2ReturnCarRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
        repository = new H2ReturnCarRepository(jdbcTemplate);
    }

    @Test
    void find() {
        CarRental carRental = repository.find(new BookingNumber(2));

        assertNotNull(carRental);

        assertEquals(new BookingNumber(2), carRental.getBookingNumber());

        Driver driver = carRental.getDriver();
        assertEquals("René", driver.getFirstname());
        assertEquals("Link", driver.getLastname());
        assertEquals("ABC", driver.getDrivingLicenceNumber());

        CarState pickupCarState = carRental.getPickupCarState();
        assertEquals(FuelLevel.FULL, pickupCarState.getFuelLevel());
        assertEquals(Odometer.of(12345), pickupCarState.getOdometer());
    }

    @Test
    void persist() {
    }
}