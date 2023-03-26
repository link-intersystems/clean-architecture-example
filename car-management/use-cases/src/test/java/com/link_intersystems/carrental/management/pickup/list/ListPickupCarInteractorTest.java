package com.link_intersystems.carrental.management.pickup.list;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListPickupCarInteractorTest {

    private MockListPickupCarRepository repository;
    private ListPickupCarInteractor interactor;

    @BeforeEach
    void setUp() {
        repository = new MockListPickupCarRepository();
        interactor = new ListPickupCarInteractor(repository);
    }

    @Test
    @FixedClock("2023-03-25 10:09:23")
    void listPickedUpCars() {
        Driver driver = new Driver("René", "Link", "ABC");
        BookingNumber bookingNumber = new BookingNumber(42);
        CarState pickupCarState = new CarState(FuelLevel.FULL, Odometer.of(5000));

        repository.addCarRental(new CarRental(bookingNumber, driver, pickupCarState));

        List<ListPickupCarResponseModel> response = interactor.listPickedUpCars();

        assertEquals(1, response.size());

        ListPickupCarResponseModel pickupCarResponse = response.get(0);
        assertEquals(42, pickupCarResponse.getBookingNumber());
        assertEquals(5000, pickupCarResponse.getOdometer());
        LocalDateTime expectedPickupDate = LocalDateTime.of(2023, 3, 25, 10, 9, 23);
        assertEquals(expectedPickupDate, pickupCarResponse.getPickupDate());
    }
}