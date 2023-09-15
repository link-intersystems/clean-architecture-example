package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JdbcUpdateCarBookingRentalRepositoryIntTest {

    private UpdateCarBookingRentalRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
        repository = new JdbcUpdateCarBookingRentalRepository(jdbcTemplate);
    }

    @Test
    void findCarBooking() {
        CarBooking carBooking = repository.findCarBooking(new BookingNumber(1));

        assertNotNull(carBooking);
        assertEquals(new BookingNumber(1), carBooking.getBookingNumber());
        assertEquals(new VIN("WMEEJ8AA3FK792135"), carBooking.getVin());
        assertEquals("Nick", carBooking.getCustomer().getFirstname());
        assertEquals("Wahlberg", carBooking.getCustomer().getLastname());
        assertNull(carBooking.getRentalState());
    }

    @Test
    void persist() {
        CarBooking carBooking = new CarBooking(new BookingNumber(42), new VIN("WMEEJ8AA3FK792135"), new Customer("René", "Link"));
        carBooking.setRentalState(RentalState.PICKED_UP);
        repository.persist(carBooking);

        Map<String, Object> persistedObject = jdbcTemplate.queryForMap("SELECT * FROM CAR_BOOKING WHERE BOOKING_NUMBER = 42");
        assertNotNull(persistedObject);

        assertEquals(42, persistedObject.get("BOOKING_NUMBER"));
        assertEquals("WMEEJ8AA3FK792135", persistedObject.get("VIN"));
        assertEquals("PICKED_UP", persistedObject.get("RENTAL_STATE"));
        assertEquals("René", persistedObject.get("CUSTOMER_FIRSTNAME"));
        assertEquals("Link", persistedObject.get("CUSTOMER_LASTNAME"));
    }
}