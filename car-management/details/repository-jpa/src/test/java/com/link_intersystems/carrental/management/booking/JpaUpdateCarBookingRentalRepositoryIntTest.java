package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.AbstractJpaManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JpaUpdateCarBookingRentalRepositoryIntTest extends AbstractJpaManagementRepositoryTest {

    private UpdateCarBookingRentalRepository repository;

    @BeforeEach
    void setUp() {

        repository = testProxy(UpdateCarBookingRentalRepository.class, new JpaUpdateCarBookingRentalRepository(entityManager));
    }

    @Test
    void persistCarBooking() {
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

    @Test
    void persistCarBookingWithoutRentalState() {
        CarBooking carBooking = new CarBooking(new BookingNumber(42), new VIN("WMEEJ8AA3FK792135"), new Customer("René", "Link"));

        repository.persist(carBooking);

        Map<String, Object> persistedObject = jdbcTemplate.queryForMap("SELECT * FROM CAR_BOOKING WHERE BOOKING_NUMBER = 42");
        assertNotNull(persistedObject);

        assertEquals(42, persistedObject.get("BOOKING_NUMBER"));
        assertEquals("WMEEJ8AA3FK792135", persistedObject.get("VIN"));
        assertNull(persistedObject.get("RENTAL_STATE"));
    }

}