package com.link_intersystems.carrental.management.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListBookingsInteractorTest {

    private ListBookingsInteractor interactor;
    private ListBookingsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ListBookingsRepositoryMock();
        interactor = new ListBookingsInteractor(repository);
    }

    @Test
    void listBookings() {
        List<CarBookingResponseModel> carBookings = interactor.listBookings();

        assertNotNull(carBookings);

        assertEquals(2, carBookings.size());

        CarBookingResponseModel firstBooking = carBookings.get(0);
        assertEquals(1, firstBooking.getBookingNumber());
        assertEquals("WMEEJ8AA3FK792135", firstBooking.getVIN());
        assertEquals("Nick", firstBooking.getCustomer().getFirstname());
        assertEquals("Wahlberg", firstBooking.getCustomer().getLastname());

        CarBookingResponseModel secondBooking = carBookings.get(1);
        assertEquals(2, secondBooking.getBookingNumber());
        assertEquals("WMEEJ8AA3FK792135", secondBooking.getVIN());
        assertEquals("René", secondBooking.getCustomer().getFirstname());
        assertEquals("Link", secondBooking.getCustomer().getLastname());
    }
}