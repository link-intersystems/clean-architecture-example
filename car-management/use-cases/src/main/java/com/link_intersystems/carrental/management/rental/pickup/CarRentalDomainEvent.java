package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.DomainEvent;
import com.link_intersystems.carrental.booking.BookingNumber;

public class CarRentalDomainEvent extends DomainEvent {

    public static final int PICKED_UP = 1;
    public static final int RETURNED = 2;

    public static CarRentalDomainEvent pickedUpEvent(BookingNumber bookingNumber){
        return new CarRentalDomainEvent(bookingNumber, PICKED_UP);
    }

    private final BookingNumber bookingNumber;
    private final int eventType;

    private CarRentalDomainEvent(BookingNumber bookingNumber, int eventType) {
        this.bookingNumber = bookingNumber;
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public BookingNumber getBookingNumber() {
        return bookingNumber;
    }
}
