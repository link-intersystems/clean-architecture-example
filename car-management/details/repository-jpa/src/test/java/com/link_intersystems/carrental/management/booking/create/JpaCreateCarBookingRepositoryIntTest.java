package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.AbstractJpaManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JpaCreateCarBookingRepositoryIntTest extends AbstractJpaManagementRepositoryTest {

    private CreateCarBookingRepository repository;

    @BeforeEach
    void setUp() {
        repository = testProxy(CreateCarBookingRepository.class, new JpaCreateCarBookingRepository(entityManager));
    }

    @Test
    void persistNew() {
        BookingNumber bookingNumber = new BookingNumber(123);
        VIN vin = new VIN("WMEEJ8AA3FK792135");
        Customer customer = new Customer("René", "Link");
        CarBooking carBooking = new CarBooking(bookingNumber, vin, customer);
        repository.persist(carBooking);

        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM MANAGEMENT.CAR_BOOKING WHERE BOOKING_NUMBER = '123'");

        assertEquals("WMEEJ8AA3FK792135", row.get("VIN"));
        assertEquals("René", row.get("CUSTOMER_FIRSTNAME"));
        assertEquals("Link", row.get("CUSTOMER_LASTNAME"));
    }

    @Test
    void persistUpdate() {
        BookingNumber bookingNumber = new BookingNumber(1);
        VIN vin = new VIN("WMEEJ8AA3FK792135");
        Customer customer = new Customer("René", "Link");
        CarBooking carBooking = new CarBooking(bookingNumber, vin, customer);
        repository.persist(carBooking);

        Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM MANAGEMENT.CAR_BOOKING WHERE BOOKING_NUMBER = '1'");

        assertEquals("WMEEJ8AA3FK792135", row.get("VIN"));
        assertEquals("René", row.get("CUSTOMER_FIRSTNAME"));
        assertEquals("Link", row.get("CUSTOMER_LASTNAME"));
    }
}