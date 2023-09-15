package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.Customer;
import com.link_intersystems.carrental.management.booking.TestJdbcCreateCarBookingRepository;
import com.link_intersystems.carrental.time.FixedClock;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JdbcPickupCarRepositoryIntTest {

    private JdbcPickupCarRepository repository;
    private JdbcTemplate jdbcTemplate;
    private TestJdbcCreateCarBookingRepository carBookingRepository;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
        repository = new JdbcPickupCarRepository(jdbcTemplate);
        carBookingRepository = new TestJdbcCreateCarBookingRepository(jdbcTemplate);
    }

    @Test
    void persistCarBookingWithoutRentalState() {
        CarBooking carBooking = new CarBooking(new BookingNumber(42), new VIN("WMEEJ8AA3FK792135"), new Customer("René", "Link"));
        carBookingRepository.persist(carBooking);

        Map<String, Object> persistedObject = jdbcTemplate.queryForMap("SELECT * FROM CAR_BOOKING WHERE BOOKING_NUMBER = 42");
        assertNotNull(persistedObject);

        assertEquals(42, persistedObject.get("BOOKING_NUMBER"));
        assertEquals("WMEEJ8AA3FK792135", persistedObject.get("VIN"));
        assertNull(persistedObject.get("RENTAL_STATE"));
    }


    @Test
    void findBooking() {
        CarBooking booking = repository.findBooking(new BookingNumber(2));

        assertNotNull(booking);
    }

    @Test
    @FixedClock("2023-03-25 13:38:23.123456")
    void persistCarRental() {
        Driver driver = new Driver("René", "Link", "ABC");
        CarState pickupState = new CarState(FuelLevel.ONE_QUARTER, Odometer.of(12345));
        CarRental carRental = new CarRental(new BookingNumber(42), driver, pickupState);

        repository.persist(carRental);

        Map<String, Object> persistedObject = jdbcTemplate.queryForMap("""
                SELECT * FROM CAR_RENTAL WHERE BOOKING_NUMBER = 42
                """);
        assertNotNull(persistedObject);

        assertEquals(42, persistedObject.get("BOOKING_NUMBER"));
        assertEquals("René", persistedObject.get("DRIVER_FIRSTNAME"));
        assertEquals("Link", persistedObject.get("DRIVER_LASTNAME"));
        assertEquals("ABC", persistedObject.get("DRIVER_LICENCE"));
        assertEquals(25, persistedObject.get("PICKUP_CAR_STATE_FUEL"));
        assertEquals(12345, persistedObject.get("PICKUP_CAR_STATE_ODOMETER"));
        Timestamp expected = Timestamp.valueOf(carRental.getPickupDateTime());
        Object actual = persistedObject.get("PICKUP_TIME");
        assertEquals(expected, actual);
    }
}