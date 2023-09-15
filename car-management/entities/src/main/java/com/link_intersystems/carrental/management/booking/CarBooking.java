package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;

import java.util.Objects;

import static java.util.Objects.*;

public class CarBooking {

    private BookingNumber bookingNumber;
    private VIN vin;
    private RentalState rentalState;
    private Customer customer;

    public CarBooking(BookingNumber bookingNumber, VIN vin, Customer customer) {
        this.bookingNumber = requireNonNull(bookingNumber);
        this.vin = requireNonNull(vin);
        this.customer = requireNonNull(customer);
    }

    public BookingNumber getBookingNumber() {
        return bookingNumber;
    }

    public VIN getVin() {
        return vin;
    }

    public RentalState getRentalState() {
        return rentalState;
    }

    public void setRentalState(RentalState rentalState) {
        this.rentalState = rentalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarBooking that = (CarBooking) o;
        return Objects.equals(getBookingNumber(), that.getBookingNumber()) &&
                Objects.equals(getVin(), that.getVin()) &&
                getRentalState() == that.getRentalState() &&
                Objects.equals(getCustomer(), that.getCustomer());
    }

    @Override
    public int hashCode() {
        return hash(
                getBookingNumber(),
                getVin(),
                getRentalState(),
                getCustomer()
        );
    }

    public Customer getCustomer() {
        return customer;
    }
}
