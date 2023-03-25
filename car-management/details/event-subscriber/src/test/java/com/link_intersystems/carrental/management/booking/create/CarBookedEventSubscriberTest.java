package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.booking.CarBookedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBookedEventSubscriberTest {

    private CarBookedEventSubscriber eventSubscriber;
    private MockCreateCarBookingUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new MockCreateCarBookingUseCase();
        eventSubscriber = new CarBookedEventSubscriber(useCase);
    }

    @Test
    void subscribedTo() {
        assertTrue(eventSubscriber.subscribedTo(CarBookedEvent.class));
    }

    @Test
    void handleEvent() {
        eventSubscriber.handleEvent(new CarBookedEvent(42, "WMEEJ8AA3FK792135"));

        CreateCarBookingRequestModel latestRequestModel = useCase.getLatestRequestModel();
        assertEquals(42, latestRequestModel.getBookingNumber());
        assertEquals("WMEEJ8AA3FK792135", latestRequestModel.getVIN());
    }
}