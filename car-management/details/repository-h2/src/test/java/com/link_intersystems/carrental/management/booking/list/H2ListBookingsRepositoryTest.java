package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.AbstractManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class H2ListBookingsRepositoryIntTest  extends AbstractManagementRepositoryTest {

    private H2ListBookingsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new H2ListBookingsRepository(jdbcTemplate);
    }

    @Test
    void findBookings() {
        List<CarBooking> bookings = repository.findBookings();

        assertEquals(1, bookings.size());
    }
}