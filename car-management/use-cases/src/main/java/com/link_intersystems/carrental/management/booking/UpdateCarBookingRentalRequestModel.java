package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.booking.BookingNumber;

public record UpdateCarBookingRentalRequestModel(BookingNumber bookingNumber, RentalState rentalState) {
}