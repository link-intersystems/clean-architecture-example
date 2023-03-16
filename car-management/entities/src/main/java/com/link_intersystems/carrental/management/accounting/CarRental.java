package com.link_intersystems.carrental.management.accounting;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.time.Period;

public class CarRental {

    private BookingNumber bookingNumber;
    private Period rentalPeriod;
    private RentalCar rentalCar;

    public CarRental(BookingNumber bookingNumber, RentalCar rentalCar, Period rentalPeriod) {
        this.bookingNumber = bookingNumber;
        this.rentalPeriod = rentalPeriod;
        this.rentalCar = rentalCar;
    }

    public BookingNumber getBookingNumber() {
        return bookingNumber;
    }

    public RentalCar getRentalCar() {
        return rentalCar;
    }

    public Period getRentalPeriod() {
        return rentalPeriod;
    }
}
