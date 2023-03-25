package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;

import java.util.Objects;

public class CarBooking {

    private Integer bookingNumber;
    private VIN vin;
    private RentalState rentalState;

    public CarBooking(int bookingNumber, VIN vin) {
        this.bookingNumber = bookingNumber;
        this.vin = vin;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public VIN getVin() {
        return vin;
    }

    public void setRentalState(RentalState rentalState) {
        this.rentalState = rentalState;
    }

    public RentalState getRentalState() {
        return rentalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarBooking that = (CarBooking) o;
        return Objects.equals(bookingNumber, that.bookingNumber) && Objects.equals(vin, that.vin) && rentalState == that.rentalState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingNumber, vin, rentalState);
    }
}
