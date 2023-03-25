package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.management.booking.CarBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateCarBookingInteractorTest {

    private CreateCarBookingInteractor createCarBookingInteractor;
    private MockCreateCarBookingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockCreateCarBookingRepository();
        createCarBookingInteractor = new CreateCarBookingInteractor(repository);
    }

    @Test
    void createCarBooking() {
        CreateCarBookingRequestModel request = new CreateCarBookingRequestModel();
        request.setBookingNumber(42);
        request.setVin("WMEEJ8AA3FK792135");

        createCarBookingInteractor.createCarBooking(request);

        CarBooking expectedCarBooking = new CarBooking(42, new VIN("WMEEJ8AA3FK792135"));
        assertEquals(expectedCarBooking, repository.getLatestPersistedCarBooking());
    }

}