package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.AbstractJpaManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JpaGetPickupCarRepositoryIntTest extends AbstractJpaManagementRepositoryTest {

    private GetPickupCarRepository repository;

    @BeforeEach
    void setUp() {
        repository = testProxy(GetPickupCarRepository.class, new JpaGetPickupCarRepository(entityManager));
    }

    @Test
    void find() {
        CarRental carRental = repository.find(new BookingNumber(2));

        assertNotNull(carRental);
        assertEquals(new BookingNumber(2), carRental.getBookingNumber());
        Driver expectedDriver = new Driver("René", "Link", "ABC");
        assertEquals(expectedDriver, carRental.getDriver());
        assertEquals(dateTime("2023-01-19", "08:00:00"), carRental.getPickupDateTime());
    }
}