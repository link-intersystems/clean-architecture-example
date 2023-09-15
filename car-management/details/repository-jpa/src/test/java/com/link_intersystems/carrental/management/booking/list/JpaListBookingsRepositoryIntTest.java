package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.AbstractJpaManagementRepositoryTest;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.booking.CarBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JpaListBookingsRepositoryIntTest extends AbstractJpaManagementRepositoryTest {


    private ListBookingsRepository repository;

    @BeforeEach
    void setUp() {
        repository = testProxy(ListBookingsRepository.class, new JpaListBookingsRepository(entityManager));
    }

    @Test
    void findBookings() {
        List<CarBooking> bookings = repository.findBookings();

        assertEquals(1, bookings.size());
    }
}