package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.FixedClock;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class H2ReturnCarRepositoryIntTest {

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
        Driver expectedDriver = new Driver("René", "Link", "ABC");
        assertEquals(expectedDriver, carRental.getDriver());
        assertEquals(dateTime("2023-01-19", "08:00:00"), carRental.getPickupDateTime());

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
    @FixedClock("2023-03-25 13:38:23.123456")
    void updateCarRental() {
        Driver driver = new Driver("René", "Link", "ABC");
        CarState pickupCarState = new CarState(FuelLevel.ONE_QUARTER, Odometer.of(12345));
        CarRental carRental = new CarRental(new BookingNumber(2), driver, pickupCarState);
        CarState returnCarState = new CarState(FuelLevel.FULL, Odometer.of(123456));
        carRental.returnCar(returnCarState, ClockProvider.now());

        repository.update(carRental);

        Map<String, Object> persistedObject = jdbcTemplate.queryForMap("""
                SELECT * FROM CAR_RENTAL WHERE BOOKING_NUMBER = 2
                """);

        assertNotNull(persistedObject);

        assertEquals(2, persistedObject.get("BOOKING_NUMBER"));
        assertEquals("René", persistedObject.get("DRIVER_FIRSTNAME"));
        assertEquals("Link", persistedObject.get("DRIVER_LASTNAME"));
        assertEquals("ABC", persistedObject.get("DRIVER_LICENCE"));
        assertEquals(25, persistedObject.get("PICKUP_CAR_STATE_FUEL"));
        assertEquals(12345, persistedObject.get("PICKUP_CAR_STATE_ODOMETER"));
        assertEquals(100, persistedObject.get("RETURN_CAR_STATE_FUEL"));
        assertEquals(123456, persistedObject.get("RETURN_CAR_STATE_ODOMETER"));

        assertEquals(Timestamp.valueOf(carRental.getPickupDateTime()), persistedObject.get("PICKUP_TIME"));
        assertEquals(Timestamp.valueOf(carRental.getReturnDateTime()), persistedObject.get("RETURN_TIME"));
    }

    @Test
    void updateNotExistentCarRental() {
        Driver driver = new Driver("René", "Link", "ABC");
        CarState pickupCarState = new CarState(FuelLevel.ONE_QUARTER, Odometer.of(12345));
        CarRental carRental = new CarRental(new BookingNumber(42), driver, pickupCarState);
        CarState returnCarState = new CarState(FuelLevel.FULL, Odometer.of(123456));
        carRental.returnCar(returnCarState, ClockProvider.now());

        assertThrows(IllegalStateException.class, () -> repository.update(carRental));
    }
}