package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.AbstractManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class H2CreateCarBookingRepositoryTest extends AbstractManagementRepositoryTest {

    private H2CreateCarBookingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new H2CreateCarBookingRepository(jdbcTemplate);
    }

    @Test
    void persist() {
        CarBooking carBooking = new CarBooking(new BookingNumber(123), new VIN("WMEEJ8AA3FK792135"));
        repository.persist(carBooking);

        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM MANAGEMENT.CAR_BOOKING WHERE BOOKING_NUMBER = '123'");

        assertEquals("WMEEJ8AA3FK792135", row.get("VIN"));
    }
}