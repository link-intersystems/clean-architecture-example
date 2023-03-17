package com.link_intersystems.carrental.management.booking;

public class CarBooking {

    private Integer bookingNumber;
    private String vin;
    private RentalState rentalState;

    public CarBooking(int bookingNumber, String vin) {
        this.bookingNumber = bookingNumber;
        this.vin = vin;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setRentalState(RentalState rentalState) {
        this.rentalState = rentalState;
    }

    public RentalState getRentalState() {
        return rentalState;
    }
}
