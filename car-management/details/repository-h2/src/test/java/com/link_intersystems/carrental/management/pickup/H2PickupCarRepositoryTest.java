package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.AbstractManagementRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class H2PickupCarRepositoryTest extends AbstractManagementRepositoryTest {
    private H2PickupCarRepository repository;

    @BeforeEach
    void setUp() {
        repository = new H2PickupCarRepository(jdbcTemplate);
    }

    @Test
    void persist() {
        BookingNumber bookingNumber = new BookingNumber(123);
        Driver driver = new Driver("John", "Doe", "abcdefg");
        CarState carState = new CarState(FuelLevel.HALF, new Odometer(11_500));
        CarPickup carPickup = new CarPickup(bookingNumber, driver, carState);
        LocalDateTime pickupDateTime = LocalDateTime.of(2023, 3, 8, 8, 30, 0);
        carPickup.setPickupDateTime(pickupDateTime);

        repository.persist(carPickup);

        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM CAR_PICKUP WHERE BOOKING_NUMBER = '123'");

        assertEquals("John", row.get("DRIVER_FIRSTNAME"));
        assertEquals("Doe", row.get("DRIVER_LASTNAME"));
        assertEquals("abcdefg", row.get("DRIVER_LICENCE"));
        assertEquals(Timestamp.valueOf(pickupDateTime), row.get("PICKUP_TIME"));
        assertEquals(50, row.get("CAR_STATE_FUEL"));
        assertEquals(11_500, row.get("CAR_STATE_ODOMETER"));
    }
}