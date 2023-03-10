package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.AbstractManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CarManagementDBExtension
class H2ListBookingsRepositoryTest extends AbstractManagementRepositoryTest {

    private H2ListBookingsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new H2ListBookingsRepository(jdbcTemplate);
    }

    @Test
    void findBookings() {
        LocalDateTime from = LocalDate.of(2023, 1, 1).atStartOfDay();
        LocalDateTime to = LocalDate.of(2023, 1, 30).atStartOfDay();

        List<CarBooking> bookings = repository.findBookings(from, to);

        Assertions.assertEquals(2, bookings.size());
    }
}