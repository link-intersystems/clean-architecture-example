package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;

import static java.util.Objects.*;

class CreateCarBookingInteractor implements CreateCarBookingUseCase {

    private CreateCarBookingRepository repository;

    public CreateCarBookingInteractor(CreateCarBookingRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public void createCarBooking(CreateCarBookingRequestModel requestModel) {
        String vinStr = requestModel.getVIN();
        VIN vin = new VIN(vinStr);
        BookingNumber bookingNumber = new BookingNumber(requestModel.getBookingNumber());

        CustomerRequestModel customerRequestModel = requestModel.getCustomerRequestModel();
        String customerFirstname = customerRequestModel.getFirstname();
        String customerLastname = customerRequestModel.getLastname();
        Customer customer = new Customer(customerFirstname, customerLastname);
        CarBooking carBooking = new CarBooking(bookingNumber, vin, customer);

        repository.persist(carBooking);
    }
}
