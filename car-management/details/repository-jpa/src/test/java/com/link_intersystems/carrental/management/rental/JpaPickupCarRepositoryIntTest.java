package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.AbstractJpaManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.TestCreateCarBookingRepository;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JpaPickupCarRepositoryIntTest extends AbstractJpaManagementRepositoryTest {

    private PickupCarRepository repository;
    private TestCreateCarBookingRepository carBookingRepository;

    @BeforeEach
    void setUp() {

        repository = testProxy(PickupCarRepository.class, new JpaPickupCarRepository(entityManager));
        carBookingRepository = new TestCreateCarBookingRepository(entityManager);
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