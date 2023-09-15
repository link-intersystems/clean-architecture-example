package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
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
        CustomerRequestModel customerModel = new CustomerRequestModel();
        customerModel.setFirstname("René");
        customerModel.setLastname("Link");
        request.setCustomerRequestModel(customerModel);

        createCarBookingInteractor.createCarBooking(request);

        BookingNumber bookingNumber = new BookingNumber(42);
        VIN vin = new VIN("WMEEJ8AA3FK792135");
        Customer customer = new Customer("René", "Link");
        CarBooking expectedCarBooking = new CarBooking(bookingNumber, vin, customer);
        assertEquals(expectedCarBooking, repository.getLatestPersistedCarBooking());
    }

}