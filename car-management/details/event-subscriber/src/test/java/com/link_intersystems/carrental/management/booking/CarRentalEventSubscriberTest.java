package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRentalDomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarRentalEventSubscriberTest {

    private CarRentalEventSubscriber eventSubscriber;
    private MockUpdateCarBookingRentalUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new MockUpdateCarBookingRentalUseCase();
        eventSubscriber = new CarRentalEventSubscriber(useCase);
    }

    @Test
    void subscribedTo() {
        assertTrue(eventSubscriber.subscribedTo(CarRentalDomainEvent.class));
    }

    @Test
    void handleEvent() {
        eventSubscriber.handleEvent(CarRentalDomainEvent.pickedUpEvent(new BookingNumber(42)));

        UpdateCarBookingRentalRequestModel latestRequestModel = useCase.getLatestRequestModel();
        assertEquals(42, latestRequestModel.bookingNumber().getValue());
        assertEquals(RentalState.PICKED_UP, latestRequestModel.rentalState());
    }
}