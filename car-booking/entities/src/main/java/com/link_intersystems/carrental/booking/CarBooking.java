package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;

import static java.util.Objects.*;

/**
 * A reservation of a {@link com.link_intersystems.carrental.rental.RentalCar} that a {@link Customer}
 * would like to rent in the future.
 */
public class CarBooking {

    private BookingNumber bookingNumber;
    private CustomerId customerId;
    private CarId carId;
    private Period bookingPeriod;

    public CarBooking(CustomerId customerId, CarId carId, Period bookingPeriod) {
        this.customerId = requireNonNull(customerId);
        this.carId = requireNonNull(carId);
        this.bookingPeriod = requireNonNull(bookingPeriod);
    }

    void setBookingNumber(BookingNumber bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public BookingNumber getBookingNumber() {
        return bookingNumber;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public CarId getCarId() {
        return carId;
    }

    public Period getBookingPeriod() {
        return bookingPeriod;
    }
}
