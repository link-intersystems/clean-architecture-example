package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.DomainEventSubscriber;

public class CreateCarBookingEventSubscriberConfig {

    public DomainEventSubscriber getCarBookedEventSubscriber(CreateCarBookingUseCase createCarBookingUseCase) {
        return new CarBookedEventSubscriber(createCarBookingUseCase);
    }

}
